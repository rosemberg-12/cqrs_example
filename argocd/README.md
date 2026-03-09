# Argo CD - comandos base (Fase 2)

## 1) Instalar Argo CD
```bash
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
```

## 2) Verificar pods
```bash
kubectl get pods -n argocd
```

## 3) Exponer Argo CD localmente (opcion simple)
```bash
kubectl port-forward svc/argocd-server -n argocd 8081:443
```
Abrir: https://localhost:8081

## 4) Obtener password inicial de admin
```bash
kubectl -n argocd get secret argocd-initial-admin-secret \
  -o jsonpath="{.data.password}" | base64 -d; echo
```

## 5) Crear Application para este proyecto
Antes, verifica en `argocd/application-cqrs.yaml`:
- `repoURL` con tu repo real.
- `targetRevision` con la rama que contiene `helm/cqrs-example` (ejemplo: `master`).

```bash
kubectl apply -f argocd/application-cqrs.yaml
kubectl get applications -n argocd
```

## 6) Ver estado de sincronizacion
```bash
kubectl describe application cqrs-example -n argocd
```

Si aparece `app path does not exist`, significa que el chart aun no esta en la rama remota observada por Argo CD. Debes hacer commit/push de la carpeta `helm/cqrs-example` a esa rama.
