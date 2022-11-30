import os
import torch

from xml.etree import ElementTree
from xml.dom import minidom
import collections
import shutil
from random import randrange, sample
from PIL import Image
from tqdm import tqdm
import numpy as np

classes = ["D00", "D10", "D20", "D40"]
idx_to_class = {i:j for i, j in enumerate(classes)}
class_to_idx = {value:key for key,value in idx_to_class.items()}

chosen_countries = ["Norway", "Japan", "United_States"]

dataset_path = "../../../projects/vc/courses/TDT17/2022/open/RDD2022/"

folder_name = "RDD_YOLO_PREPROCESSING"

not_labels = 0
labels = 0

if not os.path.exists(f"{folder_name}"):
   os.makedirs(f"{folder_name}")
   os.makedirs(f"{folder_name}/train/images")
   os.makedirs(f"{folder_name}/train/labels")
   os.makedirs(f"{folder_name}/val/images")
   os.makedirs(f"{folder_name}/val/labels")

count = 0
for country in tqdm(os.listdir(dataset_path), desc=f"Country: "):
  if country.endswith(".zip"):
    continue
  if country not in chosen_countries:
    continue
  print(f"Creating annotations for country {country}") 
  for image in tqdm(os.listdir(f"{dataset_path}/{country}/train/images"), desc=f"Image in {country}: "):
    image_name = image.split(".")[0]
    annotation_path = f"{dataset_path}/{country}/train/annotations/xmls/{image_name}.xml"
    
    try:
      infile_xml = open(annotation_path)
    except FileNotFoundError:
      continue
    tree = ElementTree.parse(infile_xml)
    root = tree.getroot()

    print_buffer = []

    im = Image.open(f"{dataset_path}/{country}/train/images/{image}").convert('RGB')
    im_w, im_h = im.size
    for obj in root.iter('object'):
      damage_type = obj.find("name").text
      bbox = obj.find("bndbox")
      xmin = int(float(bbox.find("xmin").text))
      ymin = int(float(bbox.find("ymin").text))
      xmax = int(float(bbox.find("xmax").text))
      ymax = int(float(bbox.find("ymax").text))

      center_x = (xmin + xmax) / 2 
      center_y = (ymin + ymax) / 2
      width = xmax - xmin
      height = ymax - ymin
      
      if damage_type in class_to_idx:
        class_id = class_to_idx[damage_type]

        print_buffer.append("{} {} {} {} {}".format(class_id, center_x/im_w, center_y/im_h, width/im_w, height/im_h))
    if len(print_buffer) > 0:
      labels += 1
    else: 
      not_labels += 1
    if len(print_buffer) > 0 or not_labels < 200:
      options = ["train", "val"]
      weights = [0.8, 0.2]
      
      data_type = np.random.choice(options, p=weights)
      annotation_file = open(f"{folder_name}/{data_type}/labels/{image_name}.txt", "w")
      annotation_file.write("\n".join(print_buffer))
      annotation_file.close()
      im.save(f"{folder_name}/{data_type}/images/{image_name}.jpg", "JPEG")
      count += 1
print("Done annotating file")