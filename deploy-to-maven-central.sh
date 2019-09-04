echo "decrypting secring.gpg..."
openssl aes-256-cbc -K $encrypted_a89191c56639_key -iv $encrypted_a89191c56639_iv -in $TRAVIS_BUILD_DIR/secring.gpg.enc -out $TRAVIS_BUILD_DIR/secring.gpg -d

echo "publish..."
gpg --import $TRAVIS_BUILD_DIR/secring.gpg
mvn --settings .travis/settings.xml deploy -Prelease -Dgpg.passphrase=$secretKeyPassword
