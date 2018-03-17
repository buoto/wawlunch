package main

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/buoto/wawlunch/api/transport"
)

func handler(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(map[string]interface{}{
		"alive": true,
	})

}

func main() {
	middlewares := transport.ChainMiddlewares(transport.JSONMiddleware)

	http.HandleFunc("/", middlewares(handler))

	log.Fatal(http.ListenAndServe(":8080", nil)) // TODO: Add timeout on prod
}
