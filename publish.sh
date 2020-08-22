echo "Checkout publication branch"
git fetch
git checkout publication
echo "Copy APK to main folder"
cp app/build/outputs/apk/release/app-release.apk Badminton-Umpire-Helper.apk
echo "Delete everything except APK"
find . | grep -v "Badminton-Umpire-Helper.apk" | grep -v "./.git" | xargs rm -rf
echo "Add commit and push APK"
git add -f Badminton-Umpire-Helper.apk
git add -A
git config --global user.email bjrn
git config --global user.name bjrn
git commit -m "New Badminton Umpire Helper Publication"
git push