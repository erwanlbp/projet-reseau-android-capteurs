Pour générer le jar executable :
- 

1) Edit configuration

2) Ajouter : Run Maven Goal : assembly:single

3) Jar dans le dossier /target à la racine

Pour lancer le jar
-
```
Usage: java -jar GenFlux.jar  [options]
  Options:
    -h, --help
      Affiche l'aide
    -ip, --ip-firebase
      ip du routeur de destination vers firebase
      Default: <empty string>
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
