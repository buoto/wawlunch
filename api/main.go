package main

import (
	"log"
	"net/http"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"

	"github.com/buoto/wawlunch/api/config"
	"github.com/buoto/wawlunch/api/place"
	"github.com/buoto/wawlunch/api/transport"
)

func handlerError(w http.ResponseWriter, r *http.Request) {
	http.Error(w, transport.ErrorJSON("I'm a teapot"), http.StatusTeapot)
}

func main() {

	conf, err := config.Load(config.ConfEnvName, "config/dev.json")
	if err != nil {
		log.Fatalf("Could not load config: %v", err)
	}

	db, err := gorm.Open(config.DBDriver, conf.DBConnectionString)
	if err != nil {
		log.Fatalf("Cannot connect to database: %v", err)
	}
	defer db.Close()

	middlewares := transport.ChainMiddlewares(transport.JSONMiddleware)

	http.HandleFunc("/places/", middlewares(transport.Methods(map[string]http.HandlerFunc{
		http.MethodGet:  place.List(db),
		http.MethodPost: place.Create(db),
	})))
	http.HandleFunc("/menus/", middlewares(transport.Methods(map[string]http.HandlerFunc{
		http.MethodGet: place.ListMenus(db),
	})))
	http.HandleFunc("/error/", middlewares(transport.Methods(map[string]http.HandlerFunc{
		http.MethodGet: handlerError,
	})))

	http.Handle("/images/", http.FileServer(http.Dir(conf.StaticRoot)))

	log.Println("Running server on:", conf.Addr)
	log.Fatal(http.ListenAndServe(conf.Addr, nil)) // TODO: Add timeout on prod
}
