package transport

import "net/http"

func Preflight(methods string) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Access-Control-Allow-Methods", methods)
		r.Body.Close()
	}
}
