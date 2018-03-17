package transport

import "net/http"

type Middleware func(http.HandlerFunc) http.HandlerFunc

func ChainMiddlewares(middlewares ...Middleware) func(http.HandlerFunc) http.HandlerFunc {
	return func(handler http.HandlerFunc) http.HandlerFunc {
		for _, m := range middlewares {
			handler = m(handler)
		}
		return handler
	}
}

func JSONMiddleware(f http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "application/json; charset=UTF-8")
		f(w, r)
	}
}
