package place

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/buoto/wawlunch/api/transport"
	"github.com/jinzhu/gorm"
)

func List(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var places []Place

		db.Model(&Place{}).Find(&places)

		json.NewEncoder(w).Encode(places)
	}
}

func Create(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var place Place

		err := json.NewDecoder(r.Body).Decode(&place)
		if err != nil {
			http.Error(w, transport.ErrorJSON("Error unmarshalling Place: %v", err), http.StatusBadRequest)
			return
		}

		q := db.Create(&place)

		if err = transport.DBErrors(w, q); err != nil {
			http.Error(w, transport.ErrorJSON(err.Error()), http.StatusBadRequest)
			return
		}

		w.WriteHeader(http.StatusCreated)
		json.NewEncoder(w).Encode(place)
	}
}

func ListMenus(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		params := r.URL.Query()

		var menus []Menu
		q := db.Model(&Menu{})
		if placeID, err := strconv.Atoi(params.Get("placeId")); err == nil {
			q = q.Where("place_id = ?", placeID)
		}

		q.Preload("Dishes").Find(&menus)

		json.NewEncoder(w).Encode(menus)
	}
}

func CreateMenus(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var menu Menu

		err := json.NewDecoder(r.Body).Decode(&menu)
		if err != nil {
			http.Error(w, transport.ErrorJSON("Error unmarshalling Menu: %v", err), http.StatusBadRequest)
			return
		}

		q := db.Create(&menu)
		q = db.Save(&menu)

		if err = transport.DBErrors(w, q); err != nil {
			http.Error(w, transport.ErrorJSON(err.Error()), http.StatusBadRequest)
			return
		}

		w.WriteHeader(http.StatusCreated)
		json.NewEncoder(w).Encode(menu)
	}
}

func PlaceOrder(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// TODO: check user
		payload := struct {
			PlaceID    *uint   `json:"placeId"`
			FacebookID *string `json:"facebookId"`
		}{}

		err := json.NewDecoder(r.Body).Decode(&payload)
		if err != nil {
			http.Error(w, transport.ErrorJSON("Error unmarshalling data: %v", err), http.StatusBadRequest)
			return
		}
		if payload.PlaceID == nil {
			http.Error(w, transport.ErrorJSON("Missing placeId"), http.StatusBadRequest)
			return
		}
		if payload.FacebookID == nil {
			http.Error(w, transport.ErrorJSON("Missing facebookId"), http.StatusBadRequest)
			return
		}
		order := Order{PlaceID: *payload.PlaceID, FacebookID: *payload.FacebookID}

		q := db.Create(&order).Preload("Place").Find(&order)

		if err = transport.DBErrors(w, q); err != nil {
			http.Error(w, transport.ErrorJSON(err.Error()), http.StatusBadRequest)
			return
		}
		w.WriteHeader(http.StatusCreated)
		json.NewEncoder(w).Encode(order)

	}
}

func TableCheckIn(db *gorm.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		// TODO: check user
		payload := struct {
			TableID    *uint   `json:"tableId"`
			FacebookID *string `json:"facebookId"`
		}{}
		err := json.NewDecoder(r.Body).Decode(&payload)
		if err != nil {
			http.Error(w, transport.ErrorJSON("Error unmarshalling data: %v", err), http.StatusBadRequest)
			return
		}
		if payload.TableID == nil {
			http.Error(w, transport.ErrorJSON("Missing tableId"), http.StatusBadRequest)
			return
		}
		if payload.FacebookID == nil {
			http.Error(w, transport.ErrorJSON("Missing facebookId"), http.StatusBadRequest)
			return
		}

		order := Order{FacebookID: *payload.FacebookID}
		q := db.Find(&order)
		order.TableID = *payload.TableID
		q.Save(&order)

		if err = transport.DBErrors(w, q); err != nil {
			http.Error(w, transport.ErrorJSON(err.Error()), http.StatusBadRequest)
			return
		}

		w.WriteHeader(http.StatusAccepted)
		json.NewEncoder(w).Encode(order)
	}
}
