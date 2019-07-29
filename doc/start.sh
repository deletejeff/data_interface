#/bin/sh
nohup /home/xichu/jdk1.8.0_211/bin/java -jar /home/xichu/data_interface-0.0.1-SNAPSHOT.jar > /home/xichu/logs/start.log &
tail -f /home/xichu/logs/start.log
