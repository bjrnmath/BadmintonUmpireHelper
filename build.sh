gradle clean

gpg -o app/ks.jks --passphrase="$KEYSTOREPASSPHRASE" --decrypt keystore.jks.asc

gradle assembleRelease