# Use a Node.js image for building
FROM node:18 AS build

# Set the working directory in the container
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./

#Ci install  if there is a package-lock.json file
RUN npm ci


# Copy the rest of the app's source code and build it
COPY . .
RUN npm run build

# Use a lightweight Nginx image to serve the built files
FROM nginx:alpine

# Copy the custom Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf
# Ensure this path matches


# Copy the built Vue.js app from the build stage
COPY --from=build /app/dist /usr/share/nginx/html

# Expose the application port
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
