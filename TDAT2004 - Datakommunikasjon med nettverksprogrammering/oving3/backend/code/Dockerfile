FROM gcc:4.9
RUN mkdir /code
WORKDIR /code
COPY main.cpp /code/
RUN g++ -o main main.cpp
CMD ["./main"]