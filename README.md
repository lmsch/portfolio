# Portfolio
**Luke Schipper**  
**schipperlm@gmail.com**  

## Revitalize Virtual Wellness System (JavaScript/revitalize-vws-app)

This was an 8 month final project at UPEI. A professor planned on running a 12 week program to promote healthy practices for COPD/heart disease patients. A team of 5 students, myself included, was tasked with building a health companion web app for this program. Our team met with the client and project manager regularly to gather requirements and demo prototypes. Upon completion of a project plan, we proceeded with 5 months of part-time development. As the technical lead of this project, I was reponsible for planning our system architecture, preparing our development environment, and writing frontend code. Our stack:  

* React.js with Redux
* Django HTTP API with Django REST Framework
* OAuth Resource Owner Password Flow

Our final MVP included a web app which patients used to:
* view their profile summary and lab value history
* fill out standardized surveys and view survey scores
* view graphs that visualized their lab values and survey scores over time  

Our MVP also included an admin site for managing users. Since surveys were dynamically generated on the frontend from a JSON configuration, the admin site could be used to add new surveys to the system.

About 95% of the React app was written by me, since the other 4 devs mostly worked on the Django API. See the [VWS frontend repo](https://github.com/lmsch/revitalize-vws-app) README for more information on how the app was written.

<div style="display: flex;">
  <img src="https://user-images.githubusercontent.com/31733474/174701042-398fcc4c-3b19-4fd2-8b0a-da34e7f7b4aa.png" height="200" >
  <img src="https://user-images.githubusercontent.com/31733474/174701514-2bbe952e-e25a-4d83-9446-39510e7df051.png" height="200" >
  <img src="https://user-images.githubusercontent.com/31733474/174701611-4b1ffdea-3741-4833-b998-a80fc6047a99.png" height="200" >
</div>

## Android Apps (Java/Receipt Manager Android App, Java/Tic-Tac-Toe Android App)

These two projects were completed for an Android development course. The receipt manager app, the larger of the two, was a group project with two other developers. After inputting your receipts (item quantity, item price, subtotal, tax), the app would summarize and graph your receipt history, including:  
* percentage spent on categories of items (entertainment, groceries, etc.)
* percentage spent at specific stores

My primary duty for this project was to design and implement an SQLite embedded database. I was also tasked with writing the DAOs used to interface with the database. My work can be found in this [folder](https://github.com/lmsch/portfolio/tree/main/Java/Receipt%20Manager%20Android%20App/app/src/main/java/com/hfad/appgodsproject/database/api).

The Tic-Tac-Toe app was a smaller project I completed individually. Although simple, this project gave me the opportunity to build more responsive Android layouts that correctly handled device configuration changes.

<div style="display: flex;">
  <img src="https://user-images.githubusercontent.com/31733474/174707715-51a45518-fdd7-4dfa-97f7-f0564efa2252.png" height="250" >
  <img src="https://user-images.githubusercontent.com/31733474/174707016-96b39bc9-627b-4d25-b589-599a071a9048.png" height="250" >
  <img src="https://user-images.githubusercontent.com/31733474/174707159-3f3289b0-047b-4637-872c-bd5b9782e38b.png" height="250" >
  <img src="https://user-images.githubusercontent.com/31733474/174706837-a9c0bc7c-d113-4d02-b95d-67bc4708b780.png" height="250" >
</div>

## AI and Machine Learning

I have also included a few projects from an AI/Machine Learning course. A couple of these projects are Jupyter Notebooks that contain my own explanations and observations.

#### Building a custom neural network for the MNIST dataset

The goal of this project was to write my own feedforward neural network. I then had to find a model that maximizes accuracy on the MNIST dataset (classifying images of handwritten digits). The [Jupyter Notebook](https://github.com/lmsch/portfolio/blob/main/AI%20and%20Machine%20Learning/Building%20a%20custom%20NN%20for%20MNIST%20dataset/nn_code_training.ipynb) documents how I go about writing and tuning the neural network.

#### Developing a model to predict heart failure

This project includes a Jupyter Notebook and essay. The goal was to analyze a dataset of heart failure patients to determine what lab measurements are the best indicators of death risk. I then used these key indicators to train a model that accurately predicts risk of death from heart failure. In this project, I test classical machine learning algorithms like logistic regression and decision trees. The Notebook can be found [here](https://github.com/lmsch/portfolio/blob/main/AI%20and%20Machine%20Learning/Developing%20a%20model%20to%20predict%20heart%20failure/data_analysis.ipynb).

#### Using uninformed searches to solve the 8-puzzle

For this project, I wrote an 8 puzzle game and used uninformed search algorithms to solve it (breath-first search, depth-first search, depth-limited search). For more information, see the [README](https://github.com/lmsch/portfolio/tree/main/AI%20and%20Machine%20Learning/Using%20uninformed%20searches%20to%20solve%20the%208-puzzle).

## Polygon Drawer (Java/Polygon Drawer Swing App)

This was my first Java project to use Swing. It is a program that draws an interative star polygon on the screen. Use the mouse to rotate the star or increase its size. Use the arrow keys to add more points to the star.

<img src="https://user-images.githubusercontent.com/31733474/174712488-83571cfb-9fed-4373-a9f9-6b0bf65ce180.png" height="250" >


