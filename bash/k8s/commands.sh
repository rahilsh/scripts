kubectl port-forward --context kind-firstcluster -n tidb-cluster svc/basic-prometheus 9090 > pf9090.out &
kubectl port-forward --context kind-firstcluster -n tidb-cluster svc/basic-tidb 4000 > pf4000.out &
kubectl port-forward --context kind-firstcluster -n tidb-cluster svc/basic-grafana 3000 > pf3000.out &
kubectl port-forward --context kind-firstcluster -n tidb-cluster svc/basic-prometheus 9090 > pf9090.out &
kubectl port-forward --context kind-firstcluster -n tidb-cluster svc/basic-pd 2379 > pf2379.out &
kubectl cluster-info --context kind-firstcluster
kubectl get svc -n tidb-cluster --context kind-firstcluster
kubectl describe pods --namespace tidb-cluster --context kind-firstcluster
kubectl proxy --context kind-firstcluster
