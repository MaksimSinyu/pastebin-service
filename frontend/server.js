const express = require('express');
const cors = require('cors');
const request = require('request');
const app = express();
const port = process.env.PORT || 3001;

app.use(cors());
app.use(express.json()); // Это необходимо для работы с JSON-телами запросов
app.use(express.static('public'));

// Добавьте этот маршрут
app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

// Обработчик для маршрута /paste
app.post('/paste', (req, res) => {
  const url = 'http://localhost:8080/paste';
  const options = {
    url: url,
    method: 'POST',
    json: req.body
  };
  request(options, (error, response, body) => {
    if (error) {
      res.status(500).send(error);
    } else if (response.statusCode >= 400) {
      res.status(response.statusCode).send(body);
    } else {
      res.status(200).send(body);
    }
  });
});

app.use('/api', (req, res) => {
  const url = 'http://localhost:8080/' + req.url;
  req.pipe(request(url)).pipe(res);
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
