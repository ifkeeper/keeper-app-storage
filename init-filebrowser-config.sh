#!/bin/sh
#
#   If you have not install File Browser, You can download it with following command.
#   Usage:
#
#   	$ curl -fsSL https://filebrowser.github.io/get.sh | bash
#   	  or
#   	$ wget -qO- https://filebrowser.github.io/get.sh | bash
#
#   This script just init and configuration File Browser.To do this, you need to download
#   the install-file then run this script in thw directory where the install-file is located.
#
#   The more info you can see:
#          https://www.mivm.cn/filebrowser/
#          https://docs.filebrowser.xy
#
fileBrowser=$(find ./ -name filebrowser);

echo $fileBrowser

if [ ! -n "$fileBrowser" ];then
   
   # Console info
   echo "You have not download File Browser, Or it Not in current directory!"
   echo "If you not download it you can run the following command to download it:"
   echo -e "\n"
   echo "Usage:"
   echo -e "\n"
   echo "    sudo curl -fsSL https://filebrowser.github.io/get.sh | bash"
   echo "    or"
   echo "    sudo wget -qO- https://filebrowser.github.io/get.sh | bash"
   echo -e "\n" 
   # Exit
   exit 1;
fi

# Config Databases
echo "Config db,And set default location: /etc/filebrowser.db"
./filebrowser -d /etc/filebrowser.db config init

# Config listen addr
echo "Config listen addr: 0.0.0.0"
./filebrowser -d /etc/filebrowser.db config set --address 0.0.0.0

# Config language
#
# You cat set language zh-cn,en ,etc.
echo "Config language zh-cn"
./filebrowser -d /etc/filebrowser.db config set --locale zh-cn


# Config listen port,and you can browser it whit <ip>:<port>
echo "Config listen port: 39494,and you can browser it whit <ip>:<port>"
./filebrowser -d /etc/filebrowser.db config set --port 39494

# Config log location
echo "Config log location: /var/log/filebrowser.log"
./filebrowser -d /etc/filebrowser.db config set --log /var/log/filebrowser.log

# Config admin user
echo "Config admin user, username is admin, password is admin123"
./filebrowser -d /etc/filebrowser.db users add admin admin123 --perm.admin

# Config file storage location: /home/data/filebrowser
echo "Config file storage location: /home/data/filebrowser"
mkdir -p /home/data/filebrowser
./filebrowser -d /etc/filebrowser.db config set --root /home/data/filebrowser

echo "Congratulation! file browser config init success."