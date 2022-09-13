from DbConnector import DbConnector
from tabulate import tabulate
import os
from contextlib import ExitStack
from haversine import haversine, Unit
from tqdm import tqdm
from datetime import timedelta, datetime


class ExampleProgram:

    def __init__(self):
        self.connection = DbConnector()
        self.db_connection = self.connection.db_connection
        self.cursor = self.connection.cursor
        self.activity_id = 0

    def create_table(self, table_name, attributes):
        query = f"""CREATE TABLE IF NOT EXISTS %s (
                   {attributes})
                """
        self.cursor.execute(query % table_name)
        self.db_connection.commit()

    def insert_data(self, table_name, data):
        query = "INSERT INTO %s VALUES %s"
        self.cursor.execute(query % (table_name, data))
        self.db_connection.commit()

    def fetch_data(self, query):
        self.cursor.execute(query)
        rows = self.cursor.fetchall()
        return rows

    def drop_table(self, table_name):
        print("Dropping table %s..." % table_name)
        query = "DROP TABLE IF EXISTS %s"
        self.cursor.execute(query % table_name)

    def show_tables(self):
        self.cursor.execute("SHOW TABLES")
        rows = self.cursor.fetchall()
        print(tabulate(rows, headers=self.cursor.column_names))

    def task1(self):
        def create_tables():
            self.create_table(table_name="User",
                              attributes="""
                id VARCHAR(3) NOT NULL PRIMARY KEY,
                has_label TINYINT(1)
            """)
            self.create_table(table_name="Activity",
                              attributes="""
                id INT NOT NULL PRIMARY KEY,
                user_id VARCHAR(3),
                transportation_mode VARCHAR(32),
                start_date_time DATETIME,
                end_date_time DATETIME,
                FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
            """)
            self.create_table(table_name="TrackPoint",
                              attributes="""
                id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                activity_id INT,
                lat DOUBLE,
                lon DOUBLE,
                altitude INT,
                date_days DOUBLE,
                date_time DATETIME,
                FOREIGN KEY (activity_id) REFERENCES Activity(id) ON DELETE CASCADE
            """)

        labeled_users = set(line.strip() for line in open('./dataset/dataset/labeled_ids.txt'))
        MAX_NR_OF_LINES = 2500

        def insert_user_data():
            print("Insert user data")
            user_ids = os.listdir('./dataset/dataset/Data')

            user_insert_data = ""
            for user_id in user_ids[:-1]:
                user_insert_data += f"('{user_id}', '{1 if user_id in labeled_users else 0}'),"
            user_insert_data += f"('{user_ids[-1]}', '{1 if user_id in labeled_users else 0}')"

            self.insert_data(table_name="User", data=user_insert_data)

        def insert_activity_data():
            import glob
            activity_files = glob.glob("./dataset/dataset/Data/*/*/*.plt")
            # Split up for better memory performance
            for i in tqdm(range(0, len(activity_files), 25)):
                if i + 25 > len(activity_files):
                    insert_activity_and_trackpoint(activity_files[i:])
                else:
                    insert_activity_and_trackpoint(activity_files[i:i + 25])

        def insert_activity_and_trackpoint(activity_files):
            activity_insert_data = ""
            trackpoint_insert_data = ""

            with ExitStack() as stack:
                files = [stack.enter_context(open(fname)) for fname in activity_files]
                for current_file in files:
                    current_file_path = current_file.name.split("/")
                    user_id = current_file_path[-3]
                    # unique activity id is userid + activity filename

                    with open(current_file.name) as f:
                        clean_file_data = list(filter(None, f.read().split('\n')[6:]))
                        if len(clean_file_data) > MAX_NR_OF_LINES:
                            continue

                        activity_data = list(map(lambda x: x.split(","), clean_file_data))
                        start_date = f"{activity_data[0][5]} {activity_data[0][6]}"
                        end_date = f"{activity_data[-1][5]} {activity_data[-1][6]}"
                        if user_id in labeled_users:
                            with open(f"dataset/dataset/Data/{user_id}/labels.txt") as l:
                                labeled_tracking_data_list = list(
                                    map(lambda x: x.split("\t"), list(filter(None, l.read().split('\n')[1:]))))
                                found = False
                                for labeled_tracking_data in labeled_tracking_data_list:
                                    if labeled_tracking_data[0].replace("/", "-") == start_date and \
                                            labeled_tracking_data[1].replace("/", "-") == end_date:
                                        transportation = labeled_tracking_data[2]
                                        activity_insert_data += (
                                            f"({self.activity_id}, '{user_id}', '{transportation}', '{start_date}', '{end_date}'),")
                                        found = True
                                        break

                                if not found:
                                    activity_insert_data += (
                                        f"({self.activity_id}, '{user_id}', NULL, '{start_date}', '{end_date}'),")
                                    found = False
                        else:
                            activity_insert_data += (
                                f"({self.activity_id}, '{user_id}', NULL, '{start_date}', '{end_date}'),")

                        for trackpoint_data in activity_data:
                            trackpoint_insert_data += (
                                f"(NULL, '{self.activity_id}', {trackpoint_data[0]}, {trackpoint_data[1]}, {trackpoint_data[3]}, {trackpoint_data[4]}, '{trackpoint_data[5]} {trackpoint_data[6]}'),")
                    self.activity_id += 1
            activity_insert_data = activity_insert_data[:-1]
            trackpoint_insert_data = trackpoint_insert_data[:-1]
            self.insert_data(table_name="Activity", data=activity_insert_data)
            self.insert_data(table_name="TrackPoint", data=trackpoint_insert_data)

        self.drop_table("TrackPoint")
        self.drop_table("Activity")
        self.drop_table("User")

        create_tables()
        insert_user_data()
        insert_activity_data()

    def task2(self, tasks):
        f = open("ans.txt", "w")
        if 1 in tasks:
            print("Doing task 1")
            f.write("\nTask 1\n")
            f.write(f'User: {self.fetch_data("SELECT COUNT(id) FROM User")}\n')
            f.write(f'Activity: {self.fetch_data("SELECT COUNT(id) FROM Activity")}\n')
            f.write(f'TrackPoint: {self.fetch_data("SELECT COUNT(id) FROM TrackPoint")}\n')

        if 2 in tasks:
            print("Doing task 2")

            def query_2(aggregation):
                return f"""
                                SELECT {aggregation}(User_Activities.Number_Of_Activities) FROM 
                                    (SELECT COUNT(*) as Number_Of_Activities, User.id FROM 
                                    `Activity` LEFT OUTER JOIN User 
                                    ON User.id = Activity.user_id 
                                    GROUP BY User.id) 
                                AS User_Activities;
                            """

            f.write("\nTask 2\n")
            f.write(f'AVG: {self.fetch_data(query_2("AVG"))}\n')
            f.write(f'MIN: {self.fetch_data(query_2("MIN"))}\n')
            f.write(f'MAX: {self.fetch_data(query_2("MAX"))}\n')

        if 3 in tasks:
            print("Doing task 3")
            f.write("\nTask 3\n")
            f.write(str(self.fetch_data("""
                    SELECT COUNT(*) as Number_Of_Activities, User.id FROM 
                    `Activity` LEFT OUTER JOIN User 
                    ON User.id = Activity.user_id 
                    GROUP BY User.id
                    ORDER BY Number_Of_Activities DESC
                    LIMIT 10;
                """))
                    + "\n")
        if 4 in tasks:
            print("Doing task 4")
            f.write("\nTask 4\n")
            f.write(str(
                self.fetch_data("""
                SELECT COUNT(CROSS_DATE_USERS.id) FROM
                (SELECT DISTINCT(User.id) FROM 
                `Activity` LEFT OUTER JOIN User 
                ON User.id = Activity.user_id 
                WHERE DATE(Activity.start_date_time) <> DATE(Activity.end_date_time) 
                GROUP BY User.id) as CROSS_DATE_USERS;
            """)
            ) + "\n")

        if 5 in tasks:
            print("Doing task 5")
            f.write("\nTask 5\n")
            f.write(str(
                self.fetch_data("""
                        SELECT * FROM 
                Activity a1, Activity a2
                WHERE a1.start_date_time = a2.start_date_time 
                AND a1.end_date_time = a2.end_date_time 
                AND a1.user_id = a2.user_id
                AND a1.id <> a2.id
                """)
            ) + "\n")

        if 6 in tasks:
            print("TODO doing 6")

        if 7 in tasks:
            print("Doing task 7")
            f.write("\nTask 7\n")
            f.write(str(
                self.fetch_data("""
                SELECT id FROM User
                WHERE id NOT IN (
                    SELECT DISTINCT(user_id) FROM 
                    Activity LEFT OUTER JOIN User
                    ON User.id = Activity.user_id
                    WHERE transportation_mode = "taxi");
            """)
            ) + "\n")

        if 8 in tasks:
            print("Doing task 8")
            f.write("\nTask 8\n")
            f.write(str(
                self.fetch_data("""
               SELECT COUNT(DISTINCT(User.id)), Activity.transportation_mode FROM 
               Activity LEFT OUTER JOIN User
               ON User.id = Activity.user_id
               WHERE Activity.transportation_mode IS NOT NULL
               GROUP BY Activity.transportation_mode;
               """)
            ) + "\n")

        if 9 in tasks:
            print("Doing task 9a")
            f.write("\nTask 9a\n")
            f.write(str(
                self.fetch_data("""
            SELECT COUNT(*) as Activities, YEAR(Activity.start_date_time), MONTH(Activity.start_date_time)
            FROM Activity  
            GROUP BY YEAR(Activity.start_date_time), MONTH(Activity.start_date_time)
            ORDER BY Activities DESC
            LIMIT 1
            """)
            ) + "\n")

            # 9b
            print("Doing task 9b")
            f.write("\nTask 9b\n")
            f.write(str(
                self.fetch_data("""
             SELECT user_id, COUNT(id) as activities, SUM(TIMESTAMPDIFF(SECOND,start_date_time,end_date_time))/3600 as hours 
            FROM Activity 
            WHERE YEAR(Activity.start_date_time) = 2008 
            AND MONTH(Activity.start_date_time) = 11 
            GROUP BY user_id 
            ORDER BY activities DESC
            LIMIT 2
            """)
            ) + "\n")

        if 10 in tasks:
            print("Doing task 10")
            data_points = self.fetch_data("""
            SELECT TrackPoint.lat, TrackPoint.lon, TrackPoint.activity_id, TrackPoint.date_time FROM
            TrackPoint JOIN Activity
            ON TrackPoint.activity_id = Activity.id
            WHERE Activity.user_id = 112
            AND Activity.transportation_mode = 'walk'
            AND YEAR(TrackPoint.date_time) = 2008
            ORDER BY TrackPoint.activity_id, TrackPoint.date_time;
            """)
            total_length = 0
            for i in tqdm(range(len(data_points) - 1)):
                track_point = data_points[i]
                next_track_point = data_points[i + 1]
                track_points_has_same_activity_id = track_point[2] == next_track_point[2]
                if track_points_has_same_activity_id:
                    distance_between_track_points = haversine((track_point[0], track_point[1]),
                                                              (next_track_point[0], next_track_point[1]),
                                                              unit=Unit.KILOMETERS)
                    total_length += distance_between_track_points

            f.write("\nTask 10\n")
            f.write(f"{str(round(total_length, 2))} km\n")

        if 11 in tasks:
            print("Doing task 11")
            altitude_points = self.fetch_data("""
                SELECT Activity.user_id, TrackPoint.activity_id, TrackPoint.altitude, TrackPoint.date_time FROM TrackPoint 
                JOIN Activity 
                ON TrackPoint.activity_id = Activity.id
                WHERE TrackPoint.altitude <> -777
                ORDER BY Activity.user_id, TrackPoint.activity_id, TrackPoint.date_time
                """)
            user_heights = {}
            for i in tqdm(range(len(altitude_points) - 1)):
                track_point = altitude_points[i]
                next_track_point = altitude_points[i + 1]
                track_points_has_same_activity_id = track_point[1] == next_track_point[1]
                track_points_are_from_same_user = track_point[0] == next_track_point[0]
                if track_points_has_same_activity_id and track_points_are_from_same_user:
                    next_track_point_is_higher_in_altitude = track_point[2] < next_track_point[2]
                    if next_track_point_is_higher_in_altitude:
                        try:
                            user_heights[track_point[0]] += (next_track_point[2] - track_point[2])
                        except:
                            user_heights[track_point[0]] = (next_track_point[2] - track_point[2])
            result = sorted(user_heights.items(), key=lambda item: item[1], reverse=True)

            f.write("\nTask 11\n")
            f.write(tabulate(result[:20]) + "\n")

        if 12 in tasks:
            print("Doing task 12")
            activity_points = self.fetch_data("""
            SELECT activity_id, date_time, user_id 
            FROM TrackPoint JOIN Activity 
            ON TrackPoint.activity_id = Activity.id 
            ORDER BY user_id, activity_id, date_time
            """)
            user_invalid_activities = {}
            invalid_activities = set()
            for i in tqdm(range(len(activity_points) - 1)):
                track_point = activity_points[i]
                if track_point[0] in invalid_activities:
                    continue
                next_track_point = activity_points[i + 1]
                track_points_has_same_activity_id = track_point[0] == next_track_point[0]
                track_points_are_from_same_user = track_point[2] == next_track_point[2]
                if track_points_has_same_activity_id and track_points_are_from_same_user:
                    five_minutes_in_seconds = 5 * 60
                    difference_between_track_points_in_seconds = (next_track_point[1] - track_point[1]).seconds
                    if difference_between_track_points_in_seconds >= five_minutes_in_seconds:
                        invalid_activities.add(track_point[0])
                        try:
                            user_invalid_activities[track_point[2]] += 1
                        except:
                            user_invalid_activities[track_point[2]] = 1

            b = sorted(user_invalid_activities.items(), key=lambda item: item[1], reverse=True)
            f.write("\nTask 12\n")
            f.write(f"{len(invalid_activities)}\n")
            f.write(tabulate(b) + "\n")

