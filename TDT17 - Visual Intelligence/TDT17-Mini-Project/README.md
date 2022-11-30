# TDT17-Mini-Project

This is our contribution to the mini project in the subject TDT17 - Visual Intelligence. 

# Dataset

We used the [RDD2022-dataset](https://arxiv.org/abs/2209.08538), containing annotated images of roads with damages from countries, Japan, India, the Czech Republic, Norway, the United States, and China.

# Prerequisites

You need to have [make](https://www.gnu.org/software/make/) installed on your computer to run our scripts. 

# How to run

## Labeling of data
First, we need to convert the annotations to the expected format of YOLO. This is done by running

```
make pre
```

## Train model

```
make run
```

## Detect 
```
make detect
```

# Results
Our best model can be found in `RDD_YOLO/RDD_BEST`. Here you can find the weights of the model, as well as hyperparameters, results and other related data. 

# Authors
- [Max T. Schau](https://github.com/maxschau)
- [Zaim Imran](https://github.com/Zenjjim)
