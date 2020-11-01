docker-build:
	docker build --build-arg VERSION=$(VERSION) -t mushare/httper:latest .
	docker tag mushare/httper:latest mushare/httper:$(VERSION)

docker-build-staging:
	docker build --build-arg VERSION=staging -t mushare/httper:staging .

docker-push:
	docker push mushare/httper:latest
	docker push mushare/httper:$(VERSION)

docker-push-staging:
	docker push mushare/httper:staging

ci-build-production: test docker-build docker-push

ci-build-staging: test docker-build-staging docker-push-staging