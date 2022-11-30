#!/bin/sh
#SBATCH --partition=GPUQ
#SBATCH --account=ie-idi
#SBATCH --time=24:00:00
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --gres=gpu:P100:1
#SBATCH --cpus-per-task=24
#SBATCH --mem=64G
#SBATCH --job-name=RDD_YOLO
#SBATCH --output=YOLO_TEST.out
#SBATCH --mail-user=zuimran@stud.ntnu.no
#SBATCH --mail-type=ALL

WORKDIR=${SLURM_SUBMIT_DIR}
cd ${WORKDIR}
echo "we are running from this directory: $SLURM_SUBMIT_DIR"
echo " the name of the job is: $SLURM_JOB_NAME"
echo "Th job ID is $SLURM_JOB_ID"
echo "The job was run on these nodes: $SLURM_JOB_NODELIST"
echo "Number of nodes: $SLURM_JOB_NUM_NODES"
echo "We are using $SLURM_CPUS_ON_NODE cores"
echo "We are using $SLURM_CPUS_ON_NODE cores per node"
echo "Total of $SLURM_NTASKS cores"

module purge
module load Anaconda3/2020.07
module load Python/3.8.6-GCCcore-10.2.0

pip3 install -r yolov5/requirements.txt --user -q
pip3 install wandb --use-feature=2020-resolver --user -q

python ./yolov5/detect.py \
                        --source ../../../projects/vc/courses/TDT17/2022/open/RDD2022/Norway/test/images \
                        --data RDD.yaml \
                        --weights ./RDD_YOLO/RDD_BEST/weights/best.pt \
                        --img 640 \
                        --project RDD_YOLO_TEST \
                        --name TEST_x

uname -a
