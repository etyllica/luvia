# Version date: 10-Oct-2015 (Same version as GLG2D)
version=gluegen_900-joal_626-jogl_1469-jocl_1106
jogamp=https://jogamp.org/deployment/archive/master/$version/jar
cd libs
rm gluegen-rt*.jar
rm jogl*.jar

# Downloading Gluegen
wget $jogamp/gluegen-rt.jar
wget $jogamp/gluegen-rt-natives-linux-amd64.jar
#wget $jogamp/gluegen-rt-natives-linux-i586.jar
#wget $jogamp/gluegen-rt-natives-solaris-amd64.jar
#wget $jogamp/gluegen-rt-natives-solaris-i586.jar
#wget $jogamp/gluegen-rt-natives-windows-amd64.jar
#wget $jogamp/gluegen-rt-natives-windows-i586.jar
#wget $jogamp/gluegen-rt-natives-macosx-universal.jar

# Downloading JOGL
wget $jogamp/jogl-all.jar
wget $jogamp/jogl-all-natives-linux-amd64.jar
#wget $jogamp/jogl-all-natives-linux-i586.jar
#wget $jogamp/jogl-all-natives-solaris-amd64.jar
#wget $jogamp/jogl-all-natives-solaris-i586.jar
#wget $jogamp/jogl-all-natives-windows-amd64.jar
#wget $jogamp/jogl-all-natives-windows-i586.jar
#wget $jogamp/jogl-all-natives-macosx-universal.jar
