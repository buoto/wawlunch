package main

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"

	"github.com/buoto/wawlunch/api/config"
	"github.com/buoto/wawlunch/api/transport"
)

func handler(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(map[string]interface{}{
		"alive": true,
	})

}

func main() {
	conf, err := config.Load("config/dev.json")
	if err != nil {
		log.Fatalf("Could not load config: %v", err)
	}

	db, err := gorm.Open(config.DBDriver, conf.DBConnectionString)
	if err != nil {
		log.Fatalf("Cannot connect to database: %v", err)
	}
	defer db.Close()

	middlewares := transport.ChainMiddlewares(transport.JSONMiddleware)

	http.HandleFunc("/", middlewares(handler))

	log.Println("Running server on:", conf.Addr)
	log.Fatal(http.ListenAndServe(conf.Addr, nil)) // TODO: Add timeout on prod
}
