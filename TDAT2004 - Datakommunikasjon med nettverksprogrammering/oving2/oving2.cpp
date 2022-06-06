#include <iostream>
#include <list>
#include <thread>
#include <condition_variable>
#include <iostream>
#include <functional>


using namespace std;

class Worker {
  private:
    int number_of_threads = 1;
    list<thread> list_of_worker_threads;
    list<function<void()>> list_of_tasks;
    mutex tasks_mutex;
    mutex stopped_mutex;
    mutex wait_mutex;
    bool stopped = false;
    bool wait = true;
    condition_variable cv;

  public:

    Worker(int number_of_threads1) {
      number_of_threads = number_of_threads1;
    }

    void post(function<void()> task){
        {
            lock_guard<mutex> lock(tasks_mutex);
            list_of_tasks.emplace_back(task);
        }
        {
            lock_guard<mutex> lock(wait_mutex);
            wait = false;
        }
        cv.notify_all();
    }

    void start() {
      list_of_worker_threads.clear();
      for (int i = 0; i < number_of_threads; i++) {
        list_of_worker_threads.emplace_back([this, i] {
          bool task_done = false;
          bool task_left = false;
          int nr = i;
          while(!task_done) {
            cout << "thread " << nr << endl;
            function<void()> task;
            if(!task_left) {
              unique_lock<mutex> lock(wait_mutex);
              while(wait){
                cv.wait(lock);
              }
            }
            {
              lock_guard<mutex> lock(tasks_mutex);
              if(!list_of_tasks.empty()) {
                task = *list_of_tasks.begin();
                list_of_tasks.pop_front();
                task_left = true;
              } else {
                {
                  unique_lock<mutex> lock(stopped_mutex);
                  if(stopped) {
                    task_done = true;
                  } else {
                    unique_lock<mutex> lock(wait_mutex);
                    wait = true;
                    task_left = false;
                  }
                }
              }
            }
            if(task) {
              task();
              cv.notify_one();
            }
          }
        });
      }
    }

    void stop(){
      {
        unique_lock<mutex> lock(stopped_mutex);
        stopped = true;
      }
      {
        lock_guard<mutex> lock(wait_mutex);
        wait = false;
      }
      cv.notify_all();
      for (auto &thread: list_of_worker_threads){
        thread.join();
      }
      cout<<"all threads stopped"<<endl;
    }

    void post_timeout(function<void()> f, int ms){
      this_thread::sleep_for(std::chrono::milliseconds(ms));
      post(f);
    }
};

int main() {
  Worker worker_threads(2);
  worker_threads.start();
  for (int i = 0; i < 10; i++) {
    worker_threads.post([] {
      cout<<"ree"<<endl;
    });
  }

  worker_threads.post_timeout([] {cout<<"help"<<endl;}, 1000);

  worker_threads.stop();
}