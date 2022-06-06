require(reshape2)
require(ggplot2)

#Oppgave 1
dishFile = "./dishes.csv"
dishes <- read.table(file = dishFile, header = TRUE, sep = ";")

mean_dishes <- aggregate(Score ~ Dish, dishes, mean)
ggplot(mean_dishes, aes(x = Dish, y = Score)) + geom_bar(stat = "identity")


# Oppgave 2
R <- dcast(dishes, UserName ~ Dish)

users <- R[, 1]

R <- R[, -1]

d <- dist(R, method = "euclidian")
d <- as.matrix(d)

fit <- cmdscale(d, eig = TRUE, k = 2)
x <- fit$points[, 1]
y <- fit$points[, 2]
plot(
  x,
  y,
  xlab = "x",
  ylab = "y",
  main = "Middagliste",
  type = "n"
)
text(x, y, labels = users, cex = .7)

#Oppgavae 3
require(rpart)
require(rpart.plot)

wineFile = "https://www.ntnu.no/iie/fag/big/exercises/winequalityred.csv"
wines <- read.table(file = wineFile, header = TRUE, sep = ";")
fit <-
  rpart(
    quality ~ alcohol  + fixed.acidity + volatile.acidity + citric.acid,
    method = "class",
    data = wines,
    control=rpart.control(cp=0.003)
  )
prp(fit)
printcp(fit)
