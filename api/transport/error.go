package transport

import (
	"fmt"
	"net/http"

	"github.com/jinzhu/gorm"
)

func ErrorJSON(format string, args ...interface{}) string {
	return fmt.Sprintf("{\"error\": \"%v\"}", fmt.Sprintf(format, args...))
}

func DBErrors(w http.ResponseWriter, db *gorm.DB) error {
	if errs := db.GetErrors(); len(errs) > 0 {
		for _, err := range errs {
			return err
		}
	}
	return nil
}
