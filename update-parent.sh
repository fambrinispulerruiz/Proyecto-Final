
if [ $# = 1 ]; then
  PARENT=$1
else
  PARENT=$(curl -X GET "https://nexus.incode.work/service/rest/v1/search?sort=version&repository=nightly-builds&group=org.apache.causeway.app&name=causeway-app-starter-parent" -H "accept: application/json" -s | jq '.items[0].version' | sed 's/"//g')
fi

echo "parentVersion = $PARENT"
mvn versions:update-parent -DparentVersion="[$PARENT]"

CURR=$(grep "<causeway.version>" pom.xml | head -1 | cut -d'>' -f2 | cut -d'<' -f1)
sed -i "s|<causeway.version>$CURR</causeway.version>|<causeway.version>$PARENT</causeway.version>|g" pom.xml

git add pom.xml
git commit -m "updates parent pom to $PARENT"
