run:
	chmod u+x yolo.slurm && sbatch yolo.slurm

pre:
	chmod u+x yolo_pre.slurm && sbatch yolo_pre.slurm

detect:
	chmod u+x yolo_detect.slurm && sbatch yolo_detect.slurm

queue:
	squeue -u zuimran

stop:
	scancel $(id)

stopall:
	scancel -u zuimran
