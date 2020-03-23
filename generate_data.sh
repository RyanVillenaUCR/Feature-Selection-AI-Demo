test -d _temp_/ || mkdir _temp_/
javac -d _temp_/ src/*.java

test -d output/ || mkdir output/
(echo "1 1" | java -ea -cp _temp_/ Driver > output/small80_forward_log.txt)&
(echo "2 1" | java -ea -cp _temp_/ Driver > output/large80_forward_log.txt)&
(echo "3 1" | java -ea -cp _temp_/ Driver > output/small25_forward_log.txt)&
(echo "4 1" | java -ea -cp _temp_/ Driver > output/large25_forward_log.txt)&