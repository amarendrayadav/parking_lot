echo "Changing dir to src"
cd ../
echo "Running Maven"
mvn clean package
echo "Maven run complete"
# shellcheck disable=SC1073
echo "running application with file input"
ls
#cd parking_lot/target || exit
cd target || exit
echo "running StartUp"
java -jar test-parking-lot-1.0-SNAPSHOT.jar parking_lot_file_inputs
#echo "Application run successful!"
