docker build --no-cache -t userbackend ./userAPI
docker build --no-cache -t postbackend ./postAPI
docker build --no-cache -t chatbackend ./chatAPI
docker build --no-cache -t imagebackend ./imageAPI
docker build --no-cache -t chartbackend ./chartAPI
docker build --no-cache -t whiteboardbackend ./whiteboardAPI
docker build --no-cache -t frontend ./frontend/app

kubectl apply -f kube

kubectl expose deployment userapi --type=LoadBalancer --port=8081
kubectl expose deployment postapi --type=LoadBalancer --port=8082
kubectl expose deployment chatapi --type=LoadBalancer --port=8083
kubectl expose deployment imageapi --type=LoadBalancer --port=8084
kubectl expose deployment chartapi --type=LoadBalancer --port=8888
kubectl expose deployment whiteboardapi --type=LoadBalancer --port=9898
kubectl expose deployment frontend --type=LoadBalancer --port=3000

minikube tunnel