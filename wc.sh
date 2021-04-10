find . -name "*.java"| xargs wc -l
sudo docker run -d --name zookeeper -p 2181:2181 zookeeper:3.4.14
sudo docker ps -a
sudo docker stop zookeeper