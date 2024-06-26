# 1 - Analisi

## 1.1 - Requisiti

**Sensori Lastem**

L'apparato di misurazione Lastem è organizzato come segue:

- le misure sono acquisite da delle sonde, di cui ne esistono 2 tipi: termometri e flussimetri. Le prime misurano la temperatura in gradi centigradi, le seconde il flusso di calore in W/m2.

- ogni sonda è collegata ad uno e un solo logger. Un logger può raggruppare le misure di più sonde. Inoltre comunica la sua temperatura interna e la tensione di alimentazione

- tutti i logger sono collegati ad un unico ricevitore. Ad intervalli regolari questi crea un file CSV in cui sono contenute le misure delle sonde, il suo stato operativo e quello dei logger, alla risoluzione di 1 minuto. Tali file sono caricati su un server FTP  sempre da quest'ultimo, essendo dotato di connessione ad internet.

Sono previsti i seguenti tipi di sonde:

- sonda di temperatura "aria": misura la temperatura dell'aria a 10~12cm dalla superfice del tetto;

- sonda di temperatura "superfice": misura la temperatura alla superficie de tetto, interna o esterna

- flussimetro: misura l'energia entrante o uscente dal tetto

### Requisiti funzionali

### Requisiti non funzionali

Considerando che in un futuro l'applicativo sarà modificato in modo da avere un'interfaccia web ed essere redistribuito
come container-image, si richiede che:

- la UI sia rimpiazzabile con un endpoint di altro tipo senza dover modificare la logica applicativa;

- sia possibile configurare l'applicativo tramite variabili d'ambiente e file di configurazione

## 1.2 - Analisi e modello del dominio

# 2 - Design

## 2.1 - Architettura

## 2.2 - Design dettagliato

`tech.tablesaw.filtering.predicates`

# 3 - Sviluppo

## 3.1 - Testing automatizzato

## 3.2 - Note di sviluppo

# 4 - Commenti finali

## 4.1 - Autovalutazione e lavori futuri

## 4.2 - Difficoltà incontrate e commenti per i docenti

# Appendice A - Guida utente
