package transport

import "net/http"

func Methods(lookup map[string]http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		if m, ok := lookup[r.Method]; ok {
			m(w, r)
		} else {
			http.Error(w, "", http.StatusMethodNotAllowed)
		}

	}
}
