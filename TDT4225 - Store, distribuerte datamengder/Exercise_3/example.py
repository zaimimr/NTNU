import datetime
import os
from pprint import pprint

from haversine import haversine, Unit

from DbConnector import DbConnector
from enums import Collection
from tqdm import tqdm
from contextlib import ExitStack
from tabulate import tabulate


class ExampleProgram:
    def __init__(self):
        self.connection = DbConnector()
        self.client = self.connection.client
        self.db = self.connection.db
        self.activity_id = 0

    def create_coll(self, collection_name):
        collection = self.db.create_collection(collection_name)
        print("Created collection: ", collection)

    def insert_documents(self, collection_name, docs):
        collection = self.db[collection_name]
        collection.insert_many(docs)

    def fetch_documents(self, collection_name):
        collection = self.db[collection_name]
        documents = collection.find({})
        for doc in documents:
            pprint(doc)

    def drop_coll(self, collection_name):
        collection = self.db[collection_name]
        collection.drop()

    def show_coll(self):
        collections = self.client["test"].list_collection_names()
        print(collections)

    def task1(self):
        print("Running task 1")

        self.drop_coll(Collection.USER)
        self.drop_coll(Collection.ACTIVITY)
        self.drop_coll(Collection.TRACKPOINT)

        self.create_coll(Collection.USER)
        self.create_coll(Collection.ACTIVITY)
        self.create_coll(Collection.TRACKPOINT)

        labeled_users = set(
            line.strip() for line in open("./dataset/dataset/labeled_ids.txt")
        )
        MAX_NR_OF_LINES = 2500

        def insert_user_data():
            print("Inserting user data")
            user_ids = os.listdir("./dataset/dataset/Data")
            user_insert_data = []
            for user_id in user_ids[:-1]:
                user_insert_data.append(
                    {"_id": user_id, "has_label": user_id in labeled_users}
                )

            user_insert_data.append(
                {
                    "_id": user_ids[-1],
                    "has_label": user_ids[-1] in labeled_users,
                }
            )
            self.insert_documents(Collection.USER, user_insert_data)

        def insert_activity_and_trackpoints():
            import glob

            activity_files = glob.glob("./dataset/dataset/Data/*/*/*.plt")
            # Split up for better memory performance
            for i in tqdm(range(0, len(activity_files), 25)):
                if i + 25 > len(activity_files):
                    insert_activity_and_trackpoints_in_batches(activity_files[i:])
                else:
                    insert_activity_and_trackpoints_in_batches(
                        activity_files[i: i + 25]
                    )

        def insert_activity_and_trackpoints_in_batches(activity_files):
            activity_insert_data = []
            trackpoint_insert_data = []
            with ExitStack() as stack:
                files = [stack.enter_context(open(fname)) for fname in activity_files]
                for current_file in files:
                    current_file_path = current_file.name.split("/")
                    user_id = current_file_path[-3]
                    # unique activity id is userid + activity filename

                    with open(current_file.name) as f:
                        plt_files_without_meta_data = list(
                            filter(None, f.read().split("\n")[6:])
                        )
                        if len(plt_files_without_meta_data) > MAX_NR_OF_LINES:
                            continue

                        activity_data = list(
                            map(lambda x: x.split(","), plt_files_without_meta_data)
                        )
                        start_date = f"{activity_data[0][5]} {activity_data[0][6]}"
                        end_date = f"{activity_data[-1][5]} {activity_data[-1][6]}"
                        start_date_object = datetime.datetime.strptime(start_date, "%Y-%m-%d %H:%M:%S")
                        end_date_object = datetime.datetime.strptime(end_date, "%Y-%m-%d %H:%M:%S")
                        if user_id in labeled_users:
                            with open(
                                    f"dataset/dataset/Data/{user_id}/labels.txt"
                            ) as l:
                                labeled_tracking_data_list = list(
                                    map(
                                        lambda x: x.split("\t"),
                                        list(filter(None, l.read().split("\n")[1:])),
                                    )
                                )
                                found = False
                                for labeled_tracking_data in labeled_tracking_data_list:
                                    if (
                                            labeled_tracking_data[0].replace("/", "-")
                                            == start_date
                                            and labeled_tracking_data[1].replace("/", "-")
                                            == end_date
                                    ):
                                        transportation = labeled_tracking_data[2]

                                        activity_insert_data.append(
                                            {
                                                "_id": self.activity_id,
                                                "user_id": user_id,
                                                "transportation_mode": transportation,
                                                "start_date_time": start_date_object,
                                                "end_date_time": end_date_object,
                                            }
                                        )
                                        found = True
                                        break

                                if not found:
                                    activity_insert_data.append(
                                        {
                                            "_id": self.activity_id,
                                            "user_id": user_id,
                                            "start_date_time": start_date_object,
                                            "end_date_time": end_date_object,
                                        }
                                    )
                                    found = False
                        else:
                            activity_insert_data.append(
                                {
                                    "_id": self.activity_id,
                                    "user_id": user_id,
                                    "start_date_time": start_date_object,
                                    "end_date_time": end_date_object,
                                }
                            )
                        for trackpoint_data in activity_data:
                            trackpoint_insert_data.append(
                                {
                                    "lat": float(trackpoint_data[0]),
                                    "lon": float(trackpoint_data[1]),
                                    "altitude": float(trackpoint_data[3]),
                                    "date_days": float(trackpoint_data[4]),
                                    "date_time": datetime.datetime.strptime(f"{trackpoint_data[5]} {trackpoint_data[6]}", "%Y-%m-%d %H:%M:%S"),
                                    "activity_id": self.activity_id,
                                }
                            )
                    self.activity_id += 1

            self.insert_documents(Collection.ACTIVITY, activity_insert_data)
            self.insert_documents(Collection.TRACKPOINT, trackpoint_insert_data)

            self.db[Collection.TRACKPOINT].ensure_index("activity_id", 1)

        insert_user_data()
        insert_activity_and_trackpoints()

    def task2(self, tasks):
        f = open("answers.txt", "w")
        if 1 in tasks:
            print("Doing task 1")
            f.write("\nTask 1\n")
            users_amount = self.db[Collection.USER].count()
            activities_amount = self.db[Collection.ACTIVITY].count()
            trackpoint_amount = self.db[Collection.TRACKPOINT].count()
            f.write(f"Amount of users: {users_amount}\n")
            f.write(f"Amount of activities: {activities_amount}\n")
            f.write(f"Amount of trackpoints: {trackpoint_amount}\n")

        if 2 in tasks:
            print("Doing task 2")
            f.write("\nTask 2\n")

            result = self.db[Collection.ACTIVITY].aggregate([
                {
                    "$group": {
                        "_id": "$user_id",
                        "count": {"$count": {}}
                    }
                },
                {
                    "$group": {
                        "_id": {},
                        "average": {"$avg": "$count"},
                        "minimum": {"$min": "$count"},
                        "maximum": {"$max": "$count"},
                    }
                }

            ])

            for item in result:
                f.write(f"Average: {item['average']}\n")
                f.write(f"Minimum: {item['minimum']}\n")
                f.write(f"Maximum: {item['maximum']}\n")

        if 3 in tasks:
            print("Doing task 3")
            f.write("\nTask 3\n")
            result = self.db[Collection.ACTIVITY].aggregate([
                {
                    "$group": {
                        "_id": "$user_id",
                        "numberOfActivities": {"$count": {}}
                    }
                },
                {
                    "$sort": {
                        "numberOfActivities": -1,
                    }
                },
                {"$limit": 10},
            ])
            for item in result:
                f.write(
                    f"User: {item['_id']}, activities: {item['numberOfActivities']}\n"
                )

        if 4 in tasks:
            print("Doing task 4")
            f.write("\nTask 4\n")
            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {
                        "$match": {
                            "$expr": {
                                "$eq": [
                                    {
                                        "$dateDiff": {
                                            "startDate":  "$start_date_time",
                                            "endDate":  "$end_date_time",
                                            "unit": "day",
                                        }
                                    },
                                    1
                                ]
                            }
                        }
                    },
                    {
                        "$group": {
                            "_id": {"user_id": "$user_id"},
                        }
                    },
                    {"$group": {"_id": "null", "count": {"$count": {}}}},
                ]
            )
            for item in result:
                f.write(f"{item['count']}")

        if 5 in tasks:
            print("Doing task 5")
            f.write("\nTask 5\n")

            result = self.db[Collection.ACTIVITY].aggregate([
                {
                    "$lookup": {
                        "from": f"{Collection.ACTIVITY}",
                        "let": {"userId": "$user_id", "startDate": "$start_date_time", "endDate": "$end_date_time",
                                "id": "$_id"},
                        "pipeline": [
                            {
                                "$match": {
                                    "$expr": {
                                        "$and": [
                                            {
                                                "$ne": ["$_id", "$$id"],
                                            },
                                            {
                                                "$eq": ["$start_date_time", "$$startDate"]
                                            },
                                            {
                                                "$eq": ["$end_date_time", "$$endDate"]
                                            },
                                            {
                                                "$eq": ["$user_id", "$$userId"]
                                            }
                                        ]
                                    }
                                }
                            }
                        ],
                        "as": "same_activities",
                    }
                },
                {
                    "$match": {
                        "$expr": {
                            "$gt": [{"$size": "$same_activities"}, 0]
                        }
                    }
                },
                {
                    "$addFields": {
                        "same_activity": "$same_activities._id",
                    }
                },
                {
                    "$project": {
                        "_id": 1,
                        "same_activity": 1
                    }
                }

            ])
            for r in result:
                f.write(f"{r['_id']} has also been registered as {r['same_activity']}\n")

        if 6 in tasks:
            print("Doing task 6")
            f.write("\nTask 6\n")

            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {
                        "$lookup": {
                            "from": f"{Collection.TRACKPOINT}",
                            "localField": "_id",
                            "foreignField": "activity_id",
                            "as": "trackpoints",
                        }
                    }
                ]
            )

            infected_lat = 39.97548
            infected_lon = 116.33031
            infected_position = (infected_lat, infected_lon)
            infected_time = datetime.datetime.strptime(
                "2008-08-24 15:38:00", "%Y-%m-%d %H:%M:%S"
            )

            distance_limit = 100
            time_difference_limit = 60
            user_ids_that_have_been_close = set()
            for activity in result:
                if activity["user_id"] in user_ids_that_have_been_close:
                    continue

                trackpoints = activity["trackpoints"]
                for trackpoint in trackpoints:
                    trackpoint_position = (trackpoint["lat"], trackpoint["lon"])
                    distance_between_trackpoints = haversine(
                        trackpoint_position, infected_position, unit=Unit.METERS
                    )
                    if distance_between_trackpoints > distance_limit:
                        continue
                    trackpoint_time = trackpoint["date_time"]
                    time_difference = abs(
                        (trackpoint_time - infected_time).total_seconds()
                    )

                    if time_difference <= time_difference_limit:
                        user_ids_that_have_been_close.add(activity["user_id"])

            f.write("Users that might have been infected: \n")
            for user in user_ids_that_have_been_close:
                f.write(f"User_id: {user}\n")

        if 7 in tasks:
            print("Doing task 7")
            f.write("\nTask 7\n")

            users_which_have_taken_a_taxi: list = (
                self.db[Collection.ACTIVITY]
                    .find({"transportation_mode": "taxi"}, {"user_id": 1})
                    .distinct("user_id")
            )

            users_which_have_not_taken_a_taxi: list = (
                self.db[Collection.ACTIVITY]
                    .find({"user_id": {"$nin": users_which_have_taken_a_taxi}})
                    .distinct("user_id")
            )
            for item in users_which_have_not_taken_a_taxi:
                f.write(f"{item}\n")

        if 8 in tasks:
            print("Doing task 8")
            f.write("\nTask 8\n")
            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {"$match": {"transportation_mode": {"$exists": "true"}}},
                    {
                        "$project": {
                            "transportation_mode": 1,
                            "user_id": 1,
                        }
                    },
                    {
                        "$group": {
                            "_id": {
                                "transportation_mode": "$transportation_mode",
                                "user_id": "$user_id",
                            },
                            "count": {"$sum": 1},
                        }
                    },
                    {
                        "$group": {
                            "_id": {"transportation_mode": "$_id.transportation_mode"},
                            "distinctCount": {"$sum": 1},
                        }
                    },
                    {
                        "$sort": {
                            "_id.transportation_mode": 1,
                        }
                    }
                ]
            )
            table = []
            for item in result:
                table.append(
                    [item["_id"]["transportation_mode"], item["distinctCount"]]
                )

            f.write(tabulate(table, headers=["Transportation mode", "Count"]))

        if 9 in tasks:
            print("Doing task 9a")
            f.write("\nTask 9a\n")

            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {
                        "$group": {
                            "_id": {
                                "year": {"$year": "$start_date_time"},
                                "month": {"$month": "$start_date_time"},
                            },
                            "count": {"$count": {}},
                        }
                    },
                    {"$sort": {"count": -1}},
                    {
                        "$limit": 1,
                    },
                ]
            )
            month = -1
            year = -1
            for item in result:
                month = item["_id"]["month"]
                year = item["_id"]["year"]

                f.write(f"Month {month}, year: {year}, activities: {item['count']}")

            print("Doing task 9b")
            f.write("\nTask 9b\n")

            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {
                        "$addFields": {
                            "start_year": {"$year": "$start_date_time"},
                            "start_month": {"$month": "$start_date_time"},
                            "diff": {
                                "$dateDiff": {
                                    "startDate": "$start_date_time",
                                    "endDate": "$end_date_time",
                                    "unit": "second",
                                }
                            },
                        }
                    },
                    {
                        "$match": {
                            "start_year": year,
                            "start_month": month,
                        }
                    },
                    {
                        "$group": {
                            "_id": "$user_id",
                            "activities": {"$count": {}},
                            "seconds": {"$sum": "$diff"},
                        }
                    },
                    {
                        "$sort": {
                            "activities": -1,
                        }
                    },
                    {"$limit": 2},
                    {
                        "$addFields": {
                            "hours": {
                                "$divide": ["$seconds", 3600]
                            }
                        }
                    }

                ]
            )
            table = []
            for item in result:
                table.append([item["_id"], item["activities"], item["hours"]])

            f.write(
                tabulate(
                    table, headers=["User ID", "# of activities", "Hours recorded"]
                )
            )

        if 10 in tasks:
            print("Doing task 10")
            f.write("\nTask 10\n")

            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {
                        "$match": {
                            "user_id": "112",
                            "transportation_mode": "walk",
                            "$expr": {
                                "$eq": [
                                    {"$year": "$start_date_time"},
                                    2008,
                                ]
                            },
                        }
                    },
                    {
                        "$lookup": {
                            "from": f"{Collection.TRACKPOINT}",
                            "localField": "_id",
                            "foreignField": "activity_id",
                            "as": "trackpoints",
                        }
                    },
                    {"$sort": {"activity_id": -1, "date_time": -1}},
                ]
            )
            total_length = 0
            for activity in result:
                trackpoints = activity["trackpoints"]
                for i in range(len(trackpoints) - 1):
                    track_point = trackpoints[i]
                    next_track_point = trackpoints[i + 1]
                    track_points_has_same_activity_id = (
                            track_point["activity_id"] == next_track_point["activity_id"]
                    )
                    if track_points_has_same_activity_id:
                        distance_between_track_points = haversine(
                            (track_point["lat"], track_point["lon"]),
                            (next_track_point["lat"], next_track_point["lon"]),
                        )
                        total_length += distance_between_track_points

            f.write(f"{str(round(total_length, 2))} km\n")

        if 11 in tasks:
            print("Doing task 11")
            f.write("\nTask 11\n")

            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {
                        "$match": {"altitude": {"$ne": -777}},
                    },
                    {
                        "$lookup": {
                            "from": f"{Collection.TRACKPOINT}",
                            "localField": "_id",
                            "foreignField": "activity_id",
                            "as": "trackpoints",
                        }
                    },
                ]
            )

            user_heights = {}
            for activity in result:
                trackpoints = activity["trackpoints"]
                for i in range(len(trackpoints) - 1):
                    track_point = trackpoints[i]
                    next_track_point = trackpoints[i + 1]
                    track_points_has_same_activity_id = (
                            track_point["activity_id"] == next_track_point["activity_id"]
                    )
                    if track_points_has_same_activity_id:
                        next_track_point_is_higher_in_altitude = (
                                track_point["altitude"] < next_track_point["altitude"]
                        )
                        if next_track_point_is_higher_in_altitude:
                            try:
                                user_heights[activity["user_id"]] += (
                                        next_track_point["altitude"]
                                        - track_point["altitude"]
                                )
                            except:
                                user_heights[activity["user_id"]] = (
                                        next_track_point["altitude"]
                                        - track_point["altitude"]
                                )

            sorted_result = sorted(
                user_heights.items(), key=lambda item_: item_[1], reverse=True
            )
            f.write(tabulate(sorted_result[:20], headers=["UserID", "Altitude gained"]) + "\n")

        if 12 in tasks:
            print("Doing task 12")
            f.write("\nTask 12\n")

            result = self.db[Collection.ACTIVITY].aggregate(
                [
                    {
                        "$lookup": {
                            "from": f"{Collection.TRACKPOINT}",
                            "localField": "_id",
                            "foreignField": "activity_id",
                            "as": "trackpoints",
                        }
                    },
                    {
                        "$sort": {
                            "user_id": -1,
                            "activity_id": -1,
                            "date_time": -1,
                        }
                    },
                ]
            )
            user_invalid_activities = {}
            invalid_activities = set()

            for item in result:
                user_id = item["user_id"]
                trackpoints = item["trackpoints"]
                for i in range(len(trackpoints) - 1):
                    track_point = trackpoints[i]
                    if track_point["activity_id"] in invalid_activities:
                        continue
                    next_track_point = trackpoints[i + 1]
                    track_points_has_same_activity_id = (
                            track_point["activity_id"] == next_track_point["activity_id"]
                    )
                    if track_points_has_same_activity_id:
                        five_minutes_in_seconds = 5 * 60
                        date_time_current_trackpoint = track_point["date_time"]
                        date_time_next_trackpoint = next_track_point["date_time"]

                        difference_between_track_points_in_seconds = (
                                date_time_next_trackpoint - date_time_current_trackpoint
                        ).seconds
                        if (
                                difference_between_track_points_in_seconds
                                >= five_minutes_in_seconds
                        ):
                            invalid_activities.add(track_point["activity_id"])
                            try:
                                user_invalid_activities[user_id] += 1
                            except:
                                user_invalid_activities[user_id] = 1
            b = sorted(
                user_invalid_activities.items(),
                key=lambda item_: item_[1],
                reverse=True,
            )
            f.write(f"{len(invalid_activities)}\n")
            f.write(tabulate(b, headers=["UserID", "# of invalid activities"]) + "\n")
