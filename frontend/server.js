const express = require('express');
const cors = require('cors');
const app = express();
const port = process.env.PORT || 3000;

app.use(cors());

app.use(express.static('public'));

app.use('/api', (req, res) => {
  const url = 'https://your-pastebin-api.com/api' + req.url;
  req.pipe(request(url)).pipe(res);
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});