package transport

import "fmt"

func ErrorJSON(format string, args ...interface{}) string {
	return fmt.Sprintf("{\"error\": \"%v\"}", fmt.Sprintf(format, args...))
}
