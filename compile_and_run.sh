echo "Compiling..."
test -d _temp_/ || mkdir _temp_/
javac -d _temp_/ src/*.java && echo "Compiled! Running..." && java -ea -cp _temp_/ Driver