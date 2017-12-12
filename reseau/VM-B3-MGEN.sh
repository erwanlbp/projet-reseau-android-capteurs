# Vers backbone intérieur vers capteur
ifconfig eth420 192.168.4.20/24

# Vers bdd
ifconfig eth510 192.168.5.10/24

# Pour en faire un routeur
echo 1 > /proc/sys/net/ipv4/ip_forward

/etc/init.d/zebra start