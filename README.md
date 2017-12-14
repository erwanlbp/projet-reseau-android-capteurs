# Projet-reseau-android-capteurs

## Reseau

### Conventions
* Les routeurs ont des adresse IP en dizaine (ex: 192.168.1.20)
* (Si besoin) la première interface des routeurs est vers l'extérieur du réseau
* Les routeurs sont nommés R puis un chiffre et ce qu'ils connectent
* Pour Netkit, les réseaux en extérieurs commencent par E et les intérieurs par I

### Lancement

Nous considèrerons que la variable `$PATH_RESEAU` pointe vers le dossier `reseau/` situé à la racine du projet Git.

Pour lancer le lab Netkit
```bash
lstart -d $PATH_RESEAU
```

Pour arreter le lab Netkit
```bash
lcrash -d $PATH_RESEAU
```

## Application android



## Génératon de capteurs

### Pour générer le jar executable

1) Edit configuration

2) Ajouter un run Maven Goal : assembly:single

3) Jar dans le dossier /target à la racine

### Pour lancer le jar

```
java -jar GenFlux.jar  [options]
  Options:
    -h, --help
      Affiche l'aide
    -ip, --ip-firebase
      ip du routeur de destination vers firebase
      Default: <empty string>
    -nin, --network-interface-name
      Identifiant de l'interface reseau
    -pi, --port-in
      port d'écoute du serveur, sur lequel les flux et les commandes de start / stop sont réceptionnées
      Default: -1
    -po, --port-out
      port de sortie du serveur, sur lequel les flux sont envoyés
      Default: -1
    -m, --start-mode
      Mode de lancement [gen-capteur, db-interface]
         - gen-capteur : Générer les flux et écouter les commandes de start et stop
         - capteurs : Ecoute les flux et persiste dans firebase
      Default: <empty string>
```

## Génératon de flux multimédia
```
mgen imput generation.mgn
```

#### Exemple script
```
0.0 ON 1 UDP DST 192.168.0.1/5000 PERIODIC [50.0 128]
30.0 OFF 1
30.0 ON 1 UDP DST 192.168.0.1/5000 PERIODIC [60.0 128]
60.0 OFF 1
```
• La première valeur représente l’instant de démarrage, relatif au moment t=0 de démarrage de la commande mgen
• La seconde valeur ON x précise sur quel processus on souhaite effectuer l’action
• UDP DST x.x.x.x/n défini qu’on va envoyer un flux UDP à destination de l’adresse IP x.x.x.x et du port n
• PERIODIC [N M] indique que le flux UDP transmis est périodique, que le paquet est envoyé N fois par seconde (fréquence d’envoi en Hertz) et que chaque paquet mesure M octets
• OFF x signifie qu’on met fin au processus x existant, par exemple OFF 1 pour arrêter la transmission démarrée par la commande ON 1.

## Réception de flux multimédia

### Pour une écoute classique : 
```
mgen imput reception.mgn
```

### Pour une écoute avec génération de graphique (avec trpr et gnuplot) :
```
mgen input reception.mgn | trpr mgen real | gnuplot
```

#### Exemple script
```
0.0 LISTEN UDP 5000
```
• La première valeur représente l’instant de démarrage, relatif au moment t=0 de démarrage de la commande mgen
• 5000 représente le port d'écoute




