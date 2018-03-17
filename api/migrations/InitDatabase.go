package migrations

import (
	"log"

	"github.com/buoto/wawlunch/api/config"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/sqlite"
)

func main() {
	db, err := gorm.Open(config.DBDriver, conf.DBConnectionString)
	if err != nil {
		log.Fatal("failed to connect database")
	}
	defer db.Close()

	db.AutoMigrate()
}
