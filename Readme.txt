To start the application, run the following command:
  java -jar multiple-thread-processing.jar consumers_count file

Where:
  multiple-thread-processing.jar - path to executable file
  consumers_count                - number of consumers
  file                           - path to the file with messages

For example:
  java -jar ./target/multiple-thread-processing.jar 5 ./multi_thread_messages.txt
