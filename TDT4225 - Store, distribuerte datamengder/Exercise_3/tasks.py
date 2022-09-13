from example import ExampleProgram
import sys


def main():
    program = None
    try:
        program = ExampleProgram()
        task_numbers = []
        for n in sys.argv:
            try:
                task_number = int(n)
                task_numbers.append(task_number)
            except ValueError:
                if n == "-i":
                    program.task1()
                if n == "-a":
                    program.task2([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12])

        if len(task_numbers) > 0:
            program.task2(task_numbers)


    except Exception as e:
        print("ERROR: Failed to use database:", e)
    finally:
        if program:
            program.connection.close_connection()


if __name__ == "__main__":
    main()
