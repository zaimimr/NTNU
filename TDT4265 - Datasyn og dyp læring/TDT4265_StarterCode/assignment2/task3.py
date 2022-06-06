import utils
import matplotlib.pyplot as plt
from task2a import pre_process_images, one_hot_encode, SoftmaxModel
from task2 import SoftmaxTrainer

Task3 = True
Task4AB = False
Task4D = False
Task4E = False

if __name__ == "__main__":
    # hyperparameters DO NOT CHANGE IF NOT SPECIFIED IN ASSIGNMENT TEXT
    num_epochs = 50
    learning_rate = .1
    batch_size = 32
    neurons_per_layer = [64, 10]
    momentum_gamma = .9  # Task 3 hyperparameter
    shuffle_data = True

    use_improved_sigmoid = False
    use_improved_weight_init = False
    use_momentum = False

    # Load dataset
    X_train, Y_train, X_val, Y_val = utils.load_full_mnist()
    X_train = pre_process_images(X_train)
    X_val = pre_process_images(X_val)
    Y_train = one_hot_encode(Y_train, 10)
    Y_val = one_hot_encode(Y_val, 10)


    if Task3:
        model = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history, val_history = trainer.train(num_epochs)
        shuffle_data = False
        model_no_shuffle = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_shuffle = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_no_shuffle, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_no_shuffle, val_history_no_shuffle = trainer_shuffle.train(
            num_epochs)
        shuffle_data = True

        # Improved weight 
        use_improved_weight_init = True
        model_improved_weight = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_improved_weight = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_improved_weight, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_improved_weight, val_history_improved_weight = trainer_improved_weight.train(num_epochs)

        # Improved sigmoid
        use_improved_sigmoid = True
        model_improved_sigmoid = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_improved_sigmoid = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_improved_sigmoid, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_improved_sigmoid, val_history_improved_sigmoid = trainer_improved_sigmoid.train(num_epochs)

        # Momentum
        use_momentum = True
        learning_rate = 0.02
        model_momentum = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_momentum = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_momentum, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_momentum, val_history_momentum = trainer_momentum.train(num_epochs)

        plt.subplot(1, 2, 1)
        utils.plot_loss(train_history["loss"], "Task 2", npoints_to_average=10)
        utils.plot_loss(train_history_no_shuffle["loss"], "Task 2 - No dataset shuffling", npoints_to_average=10)
        utils.plot_loss(train_history_improved_weight["loss"], "Task 3a - Improved Weights", npoints_to_average=10)
        utils.plot_loss(train_history_improved_sigmoid["loss"], "Task 3b - Improved Sigmoid", npoints_to_average=10)
        utils.plot_loss(train_history_momentum["loss"], "Task 3c - Momentum", npoints_to_average=10)
        plt.ylabel("Loss")
        plt.ylim([0, .4])
        plt.legend()
        plt.subplot(1, 2, 2)
        plt.ylim([0.85, .99])
        utils.plot_loss(val_history["accuracy"], "Task 2")
        utils.plot_loss(val_history_no_shuffle["accuracy"], "Task 2 - No Dataset Shuffling")
        utils.plot_loss(val_history_improved_weight["accuracy"], "Task 3a - Improved Weights")
        utils.plot_loss(val_history_improved_sigmoid["accuracy"], "Task 3b - Improved Sigmoid")
        utils.plot_loss(val_history_momentum["accuracy"], "Task 3c - Momentum")
        plt.ylabel("Validation Accuracy")
        plt.legend()
        plt.show()
    
    if Task4AB:
        use_improved_sigmoid = True
        use_improved_weight_init = True

        use_momentum = True
        learning_rate = 0.02

        model_64 = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_64 = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_64, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_64, val_history_64 = trainer_64.train(num_epochs)

        neurons_per_layer = [32, 10]
        model_32 = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_32 = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_32, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_32, val_history_32 = trainer_32.train(num_epochs)

        neurons_per_layer = [128, 10]
        model_128 = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_128 = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_128, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_128, val_history_128 = trainer_128.train(num_epochs)

        plt.subplot(1, 2, 1)
        utils.plot_loss(train_history_32["loss"], "Task 4a - 32 hidden units", npoints_to_average=10)
        utils.plot_loss(train_history_64["loss"], "Task 3 - 64 hidden units", npoints_to_average=10)
        utils.plot_loss(train_history_128["loss"], "Task 4b - 128 hidden units", npoints_to_average=10)
        plt.ylabel("Loss")
        plt.ylim([0, .5])
        plt.legend()
        plt.subplot(1, 2, 2)
        plt.ylim([.91, .98])
        utils.plot_loss(val_history_32["accuracy"], "Task 4a - 32 hidden units")
        utils.plot_loss(val_history_64["accuracy"], "Task 3 - 64 hidden units")
        utils.plot_loss(val_history_128["accuracy"], "Task 4b - 128 hidden units")
        plt.ylabel("Validation Accuracy")
        plt.legend()
        plt.show()

    if Task4D:
        use_improved_sigmoid = True
        use_improved_weight_init = True

        use_momentum = True
        learning_rate = 0.02

        neurons_per_layer = [64, 10]
        model = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history, val_history = trainer.train(num_epochs)
        
        neurons_per_layer = [60, 60, 10]
        model_2_laye = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_2_laye = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_2_laye, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_2_laye, val_history_2_laye = trainer_2_laye.train(num_epochs)
        
        plt.subplot(1, 2, 1)
        utils.plot_loss(train_history["loss"], "Task 4d - 1 layer - training loss", npoints_to_average=10)
        utils.plot_loss(val_history["loss"], "Task 4d - 1 layer - validation loss", npoints_to_average=10)
        utils.plot_loss(train_history_2_laye["loss"], "Task 4d - 2 layer - training loss", npoints_to_average=10)
        utils.plot_loss(val_history_2_laye["loss"], "Task 4d - 2 layer - validation loss", npoints_to_average=10)
        plt.ylabel("Loss")
        plt.ylim([0, .5])
        plt.legend()
        plt.subplot(1, 2, 2)
        plt.ylim([.91, 1.2])
        utils.plot_loss(train_history["accuracy"], "Task 4d - 1 layer - training accuracy")
        utils.plot_loss(val_history["accuracy"], "Task 4d - 1 layer - validation accuracy")
        utils.plot_loss(train_history_2_laye["accuracy"], "Task 4d - 2 layer - training accuracy")
        utils.plot_loss(val_history_2_laye["accuracy"], "Task 4d - 2 layer - validation accuracy")
        plt.ylabel("Validation Accuracy")
        plt.legend()
        plt.show()

    if Task4E:
        use_improved_sigmoid = True
        use_improved_weight_init = True

        use_momentum = True
        learning_rate = 0.02

        neurons_per_layer = [64, 10]
        model = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history, val_history = trainer.train(num_epochs)
        
        neurons_per_layer = [60, 60, 10]
        model_2_laye = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_2_laye = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_2_laye, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_2_laye, val_history_2_laye = trainer_2_laye.train(num_epochs)

        neurons_per_layer = [64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 10]
        model_10_layer = SoftmaxModel(
            neurons_per_layer,
            use_improved_sigmoid,
            use_improved_weight_init)
        trainer_10_layer = SoftmaxTrainer(
            momentum_gamma, use_momentum,
            model_10_layer, learning_rate, batch_size, shuffle_data,
            X_train, Y_train, X_val, Y_val,
        )
        train_history_10_layer, val_history_10_layer = trainer_10_layer.train(num_epochs)
        
        plt.subplot(1, 2, 1)
        utils.plot_loss(train_history["loss"], "Task 4d - 1 layer - training loss", npoints_to_average=10)
        utils.plot_loss(val_history["loss"], "Task 4d - 1 layer - validation loss", npoints_to_average=10)
        utils.plot_loss(train_history_2_laye["loss"], "Task 4d - 2 layer - training loss", npoints_to_average=10)
        utils.plot_loss(val_history_2_laye["loss"], "Task 4d - 2 layer - validation loss", npoints_to_average=10)
        utils.plot_loss(train_history_10_layer["loss"], "Task 4e - 10 layer - validation loss", npoints_to_average=10)
        utils.plot_loss(val_history_10_layer["loss"], "Task 4e - 10 layer - validation loss", npoints_to_average=10)
        plt.ylabel("Loss")
        plt.ylim([0, .5])
        plt.legend()
        plt.subplot(1, 2, 2)
        plt.ylim([.91, 1.2])
        utils.plot_loss(train_history["accuracy"], "Task 4d - 1 layer - training accuracy")
        utils.plot_loss(val_history["accuracy"], "Task 4d - 1 layer - validation accuracy")
        utils.plot_loss(train_history_2_laye["accuracy"], "Task 4d - 2 layer - training accuracy")
        utils.plot_loss(val_history_2_laye["accuracy"], "Task 4d - 2 layer - validation accuracy")
        utils.plot_loss(train_history_10_layer["accuracy"], "Task 4e - 10 layer - training accuracy")
        utils.plot_loss(val_history_10_layer["accuracy"], "Task 4e - 10 layer - validation accuracy")
        plt.ylabel("Validation Accuracy")
        plt.legend()
        plt.show()