gradle clean

echo "$KEYSTOREPASSPHRASE" | gpg --batch --yes --passphrase-fd 0 -o app/ks.jks --decrypt keystore.jks.asc

gradle assembleRelease