# DMG

Este projeto é um beta para o Monitoramento da Diabetes Mellitus Gestacional(DMG) baseado na publicação Tratamento do Diabetes Mellitus Gestacional no Brasil.
Referência: 
https://www.diabetes.org.br/profissionais/images/pdf/Consenso_Brasileiro_Manejo_DMG_2019.pdf

Créditos ao Sandro Giacom pela disponibilização da estrutura base deste projeto.

## Parte 1 - base app:

### Requisitos:

**Docker e Make (Opcional)**

**Java 14**

Ajuda para instalar as ferramentas:

https://github.com/sandrogiacom/k8s

### Construir(build) e executar(run) aplicação:

Spring boot e banco de dados MySQL executando no docker

**Clone do repositório**
```bash
git clone https://github.com/reysonbarros/monitoramento-glicemia-dmg.git
```

**Construir aplicação**
```bash
cd monitoramento-glicemia-dmg
mvn clean install
```

**Inicializar o banco de dados**
```bash
make run-db
```

**Executar aplicação**
```bash
java --enable-preview -jar target/dmg-kubernetes.jar
```

**Verificação**

http://localhost:8080/app/glicemias

## Parte 2 - app no Docker:

Criar um Dockerfile:

```yaml
FROM openjdk:14-alpine
RUN mkdir /usr/myapp
COPY target/dmg-kubernetes.jar /usr/myapp/app.jar
WORKDIR /usr/myapp
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java --enable-preview $JAVA_OPTS -jar app.jar" ]
```

**Construir aplicação e imagem docker**

```bash
make build
```

Criar e executar o banco de dados
```bash
make run-db
```

Criar e executar a aplicação
```bash
make run-app
```

**Verificação**

http://localhost:8080/app/glicemia

Parar tudo(app + banco):

`
docker stop mysql57 myapp
`

## Parte 3 - app no Kubernetes:

Nós temos uma aplicação e imagem executando no docker.
Agora nós implantamos uma aplicação em um cluster kubernetes executando em nossa máquina.

Prepare

### Iniciar minikube
`
make k-setup
`
 iniciar minikube, habilitar ingress e criar namespace dev-to

### Verificar IP(Internet Protocol)

`
minikube -p dev.to ip
`

### Painel de Controle do Minikube

`
minikube -p dev.to dashboard
`

### Implantar banco de dados

create mysql deployment and service

`
make k-deploy-db
`

`
kubectl get pods -n dev-to
`

ou

`
watch k get pods -n dev-to
`


`
kubectl logs -n dev-to -f <pod_name>
`

`
kubectl port-forward -n dev-to <pod_name> 3306:3306
`

## Construir aplicação e implantar

Construir app

`
make k-build-app
` 

Criar imagem docker dentro da máquina minikube

`
make k-build-image
`

ou

`
make k-cache-image
`  

Criar implantação do app e serviço:

`
make k-deploy-app
` 

**Verificar**

`
kubectl get services -n dev-to
`

Para acessar o app:

`
minikube -p dev.to service -n dev-to myapp --url
`

Ex:

http://192.168.99.116:31479/app/glicemias


## Verificar pods

`
kubectl get pods -n dev-to
`

`
kubectl -n dev-to logs myapp-6bbb46f69f-75ld4
`

## Mapear para dev.local

Obter minikube IP
`
minikube -p dev.to ip
` 

Editar `hosts` 

`
sudo vim /etc/hosts
`
ou

`
No Windows abrir o cmd como Administrador:
C:\Windows\system32\drivers\etc\hosts
ex: 192.168.99.116 dev.local
`

Réplicas
`
kubectl get rs -n dev-to
`

Obter and deletar pod
`
kubectl get pods -n dev-to
`

`
kubectl delete pod -n dev-to myapp-f6774f497-82w4r
`

Escala
`
kubectl -n dev-to scale deployment/myapp --replicas=2
`

Testar réplicas
`
while true
do curl "http://dev.local/app/glicemias"
echo
sleep 2
done
`

## Verificar url do app
`minikube -p dev.to service -n dev-to myapp --url`

Alterar seu IP and PORT conforme necessário

`
curl -X GET http://dev.local/app/glicemias
`

Adicionar nova Glicemia
`
curl --location --request POST 'http://dev.local/app/glicemias' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Paciente 1",
    "data": "2021-03-23",
    "periodo": "JEJUM", 
    "refeicao": null,
    "valor": 71.16    
}'
------------------
curl --location --request POST 'http://dev.local/app/glicemias' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Paciente 1",
    "data": "2021-03-23",
    "periodo": "POS_PRANDIAL_1H", 
    "refeicao": "LANCHE",
    "valor": 132    
}'
------------------
curl --location --request POST 'http://dev.local/app/glicemias' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Paciente 1",
    "data": "2021-03-23",
    "periodo": "POS_PRANDIAL_1H", 
    "refeicao": "ALMOCO",
    "valor": 139.99    
}'
------------------
curl --location --request POST 'http://dev.local/app/glicemias' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "Paciente 1",
    "data": "2021-03-22",
    "periodo": "POS_PRANDIAL_1H", 
    "refeicao": "JANTAR",
    "valor": 80.44    
}'


`

## Parte 4 - debug app:

adicionar JAVA_OPTS: "-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n -Xms256m -Xmx512m -XX:MaxMetaspaceSize=128m"
 
`
kubectl get pods -n=dev-to
`

`
kubectl port-forward -n=dev-to <pod_name> 5005:5005
`

## KubeNs e Stern

`
kubens dev-to
`

`
stern myapp
` 

## Iniciar toda a aplicação

`make k:all`


## Referências

https://kubernetes.io/docs/home/

https://minikube.sigs.k8s.io/docs/

## Comandos úteis

```
##Lista de perfis

minikube profile list

kubectl top node

kubectl top pod <nome_do_pod>
```
