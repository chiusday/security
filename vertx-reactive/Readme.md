# vertx-reactive
This project is a reactive version of the vertx project multiple-tables in this same repo.
Not all Async Handlers are converted to reactive though. Most of the core abstracts and interfaces are just reused since their logic and functionality remains solid even now that I'm converting the services to be reactive.

Also, vertx web is replaced with spring web just so this can be used as the business service for the cloud and oauth2 repository projects.
