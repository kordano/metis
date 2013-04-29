# metis

A Clojure library for statistics.

## Usage

Use descriptive namespace for common functions such as mean, median, quartiles, variance and standard deviation.

Later there should be a Server and Client providing these functions and much much more. ;-)

## Using the Node.js Webserver

Make sure nodejs and npm is installed, otherwise
```bash
  $ sudo apt-get install nodejs
  $ sudo apt-get install npm
```
Install needed packages for node.js and compile Clojurescript

```bash
  $ npm install
  $ lein cljsbuild once
```

Start node.js server

```bash
  $ nodejs resources/public/js/main.js
```

Open with your favorite browser http://localhost:3000/
## License

Copyright © 2013 Konrad Kühne

Distributed under the Eclipse Public License, the same as Clojure.
