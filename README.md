- Server for https://github.com/oracle/graalvm-reachability-metadata/issues/202
 and https://github.com/oracle/graal/issues/5913

- Execute the following command to reproduce the Error Log.
```shell
sudo apt install unzip zip curl sed -y
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 22.3.1.r17-grl
sdk use java 22.3.1.r17-grl

gu install native-image
sudo apt-get install build-essential libz-dev zlib1g-dev -y

git clone git@github.com:linghengqian/driver-classloader-graalvm-test.git
cd ./driver-classloader-graalvm-test/
./gradlew -Pagent clean test
./gradlew metadataCopy --task test
./gradlew clean nativeTest
```