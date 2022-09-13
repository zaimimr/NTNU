import torch
import torch.nn as nn
import matplotlib.pyplot as plt
import tqdm
import numpy as np
import utils
import dataloaders
import torchvision
from trainer import Trainer
torch.random.manual_seed(0)
np.random.seed(0)


# Load the dataset and print some stats
batch_size = 64

image_transform = torchvision.transforms.Compose([
    torchvision.transforms.ToTensor(),
])
image_transform_normal = torchvision.transforms.Compose([
    torchvision.transforms.ToTensor(),
    torchvision.transforms.Normalize([0.5], [0.5])
])

dataloader_train, dataloader_test = dataloaders.load_dataset(batch_size, image_transform)
dataloader_train_normal, dataloader_test_normal = dataloaders.load_dataset(batch_size, image_transform_normal)


def create_model():
    """
        Initializes the mode. Edit the code below if you would like to change the model.
    """
    model = nn.Sequential(
        nn.Flatten(),  # Flattens the image from shape (batch_size, C, Height, width) to (batch_size, C*height*width)
        nn.Linear(28*28*1, 10)
        # No need to include softmax, as this is already combined in the loss function
    )
    # Transfer model to GPU memory if a GPU is available
    model = utils.to_cuda(model)
    return model

def create_ReLU_model():
    """
        Initializes the mode. Edit the code below if you would like to change the model.
    """
    hidden_layer = 64

    model = nn.Sequential(
        nn.Flatten(),  # Flattens the image from shape (batch_size, C, Height, width) to (batch_size, C*height*width)
        nn.Linear(28*28*1, hidden_layer),
        nn.ReLU(),
        nn.Linear(hidden_layer, 10)
        # No need to include softmax, as this is already combined in the loss function
    )
    # Transfer model to GPU memory if a GPU is available
    model = utils.to_cuda(model)
    return model


model = create_model()
relu_model = create_ReLU_model()


# Hyperparameters
learning_rate_full = 1.0
learning_rate = 0.0192
num_epochs = 5


# Use CrossEntropyLoss for multi-class classification
loss_function = torch.nn.CrossEntropyLoss()

# Define optimizer (Stochastic Gradient Descent)
optimizer = torch.optim.SGD(model.parameters(), lr=learning_rate)
# optimizer_better_lr = torch.optim.SGD(model.parameters(), lr=learning_rate_full)
relu_optimizer = torch.optim.SGD(relu_model.parameters(), lr=learning_rate)


# trainer = Trainer(
#     model=model,
#     dataloader_train=dataloader_train,
#     dataloader_test=dataloader_test,
#     batch_size=batch_size,
#     loss_function=loss_function,
#     optimizer=optimizer
# )
# train_loss_dict, test_loss_dict = trainer.train(num_epochs)

trainer_normal = Trainer(
    model=model,
    dataloader_train=dataloader_train_normal,
    dataloader_test=dataloader_test_normal,
    batch_size=batch_size,
    loss_function=loss_function,
    optimizer=optimizer
)
train_loss_dict_normal, test_loss_dict_normal = trainer_normal.train(num_epochs)

# trainer_lr = Trainer(
#     model=model,
#     dataloader_train=dataloader_train_normal,
#     dataloader_test=dataloader_test_normal,
#     batch_size=batch_size,
#     loss_function=loss_function,
#     optimizer=optimizer_better_lr
# )
# lr_train_loss_dict, lr_test_loss_dict = trainer_lr.train(num_epochs)

relu_trainer = Trainer(
    model=relu_model,
    dataloader_train=dataloader_train_normal,
    dataloader_test=dataloader_test_normal,
    batch_size=batch_size,
    loss_function=loss_function,
    optimizer=relu_optimizer
)
relu_train_loss_dict, relu_test_loss_dict = relu_trainer.train(num_epochs)


if False:
    weight = list(model.children())[1].weight.cpu().data


    for w in range(weight.shape[0]):
        im = np.zeros((28, 28))

        min = weight[w, :].min()
        max = weight[w, :].max()

        for y in range(28):
            for x in range(28):
                im[y, x] = float((weight[w, y * 28 + x] - min) / (max - min))

        plt.imsave("weights/" + str(w) + "_weight_image.png", im, cmap="gray")

# We can now plot the training loss with our utility script

# # Plot original
if False:
    utils.plot_loss(train_loss_dict, label="Train Loss")
    utils.plot_loss(test_loss_dict, label="Test Loss")
    # Limit the y-axis of the plot (The range should not be increased!)
    plt.ylim([0, 1])
    plt.legend()
    plt.xlabel("Global Training Step")
    plt.ylabel("Cross Entropy Loss")
    plt.savefig("image_solutions/task_4a_original.png")

    plt.show()

    torch.save(model.state_dict(), "saved_model_original.torch")
    final_loss, final_acc = utils.compute_loss_and_accuracy(
        dataloader_test_normal, model, loss_function)
    print(f"Loss: {final_loss}. Accuracy: {final_acc}")

# normalized
# Plot original vs normalized results
if False:
    utils.plot_loss(train_loss_dict, label="Train Loss")
    utils.plot_loss(train_loss_dict_normal, label="Train Loss, Normalized")
    utils.plot_loss(test_loss_dict, label="Test Loss")
    utils.plot_loss(test_loss_dict_normal, label="Test Loss, Normalized")
    plt.ylim([0, 1])
    plt.legend()
    plt.xlabel("Number of Images Seen")
    plt.ylabel("Cross Entropy Loss")
    plt.savefig("image_solutions/task_4a_normalized.png")
    plt.show()
    torch.save(model.state_dict(), "saved_model_normalized.torch")
    final_loss, final_acc = utils.compute_loss_and_accuracy(
        dataloader_test_normal, model, loss_function)
    print(f"Final Test loss: {final_loss}. Final Test accuracy: {final_acc}")


# Plot bad learning rate
if False:
    utils.plot_loss(lr_train_loss_dict, label="Train Loss")
    utils.plot_loss(lr_test_loss_dict, label="Test Loss")
    plt.ylim([0, 20])
    plt.legend()
    plt.xlabel("Number of Images Seen")
    plt.ylabel("Cross Entropy Loss")
    plt.savefig("image_solutions/task_4a_high_lr.png")

    plt.show()
    torch.save(model.state_dict(), "saved_model_high_lr.torch")
    final_loss, final_acc = utils.compute_loss_and_accuracy(
        dataloader_test_normal, model, loss_function)
    print(f"Normalized Final Test Cross Entropy Loss: {final_loss}. Final Test accuracy: {final_acc}")

# Plot normalized vs hidden layer model results
if True:
    utils.plot_loss(train_loss_dict_normal, label="Train Loss")
    utils.plot_loss(relu_train_loss_dict, label="Train Loss, Hidden Layer")
    utils.plot_loss(test_loss_dict_normal, label="Test Loss")
    utils.plot_loss(relu_test_loss_dict, label="Test Loss, Hidden Layer")
    plt.ylim([0, 1])
    plt.legend()
    plt.xlabel("Number of Images Seen")
    plt.ylabel("Cross Entropy Loss")
    plt.savefig("image_solutions/task_4a_relu.png")

    plt.show()
    torch.save(relu_model.state_dict(), "saved_model_relu.torch")
    final_loss, final_acc = utils.compute_loss_and_accuracy(
        dataloader_test_normal, relu_model, loss_function)
    print(f"Normalized Final Test Cross Entropy Loss: {final_loss}. Final Test accuracy: {final_acc}")