package config

import (
	"encoding/json"
	"fmt"
	"os"
)

type Config struct {
	Addr               string `json:"addr"`
	DBConnectionString string `json:"dbConnectionString"`
	StaticRoot         string `json:"staticRoot"`
	URL                string `json:"url"`
}

func Load(pathEnv, path string) (Config, error) {
	if confPath, ok := os.LookupEnv(pathEnv); ok {
		path = confPath
	}
	var conf Config

	file, err := os.Open(path)
	if err != nil {
		return conf, fmt.Errorf("error openning config file: %v", err)
	}
	defer file.Close()

	err = json.NewDecoder(file).Decode(&conf)
	if err != nil {
		return conf, fmt.Errorf("error unmarshalling: %v", err)
	}

	return conf, nil
}
