#/bin/sh
nohup /home/mc/jdk1.8.0_211/bin/java -jar /home/mc/data_interface-0.0.1-SNAPSHOT.jar > /home/mc/logs/start.log &
tail -f /home/mc/logs/start.log
