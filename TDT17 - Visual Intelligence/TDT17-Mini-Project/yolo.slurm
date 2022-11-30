#!/bin/sh
#SBATCH --partition=GPUQ
#SBATCH --account=ie-idi
#SBATCH --time=24:00:00
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --gres=gpu:A100m40:2
#SBATCH --cpus-per-task=24
#SBATCH --mem=64G
#SBATCH --job-name=RDD_YOLO
#SBATCH --output=YOLO_RDD.out
#SBATCH --mail-user=zuimran@stud.ntnu.no
#SBATCH --mail-type=ALL
WORKDIR=${SLURM_SUBMIT_DIR}
cd ${WORKDIR}
echo “we are running from this directory: $SLURM_SUBMIT_DIR”
echo ” the name of the job is: $SLURM_JOB_NAME”
echo “Th job ID is $SLURM_JOB_ID”
echo “The job was run on these nodes: $SLURM_JOB_NODELIST”
echo “Number of nodes: $SLURM_JOB_NUM_NODES”
echo “We are using $SLURM_CPUS_ON_NODE cores”
echo “We are using $SLURM_CPUS_ON_NODE cores per node”
echo “Total of $SLURM_NTASKS cores”

module purge
module load Anaconda3/2020.07
module load Python/3.8.6-GCCcore-10.2.0

pip3 install -r yolov5/requirements.txt --user -q
pip3 install wandb --use-feature=2020-resolver --user -q

python3 -m torch.distributed.launch \
                        --nproc_per_node=2 \
                        ./yolov5/train.py \
                        --device 0,1 \
                        --batch 64 \
                        --epochs 300 \
                        --workers 16 \
                        --data RDD.yaml \
                        --img 640 \
                        --weights yolov5m.pt \
                        --project RDD_YOLO \
                        --name RDD_x \
                        --cache ram \
                        --cache disk
uname -a
