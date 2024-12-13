<script setup lang="ts">
import { ref, onMounted } from 'vue';
import FormComponent from './FormComponent.vue';
import BasicInputComponent from './BasicInputComponent.vue';
import FormButtonComponent from './FormButtonComponent.vue';
import axios from "axios";

interface ArtWork {
  title: string;
  description: string;
  price: number;
  mediaUrl: string;
  type: boolean;
  isSold: boolean;
  tags: ArtWorkTag[];
}

const artwork = ref<ArtWork>({
  title: '',
  description: '',
  price: 0,
  mediaUrl: '',
  type: true,
  tags: [],
  isSold: false,
});

interface ArtWorkTag {
  tagType: string;
  tagValue: string;
  tagId: number;
}

const loading = ref(false);
const tagsByType = ref<Record<string, ArtWorkTag[]>>({});
const showTags = ref(false);

const toggleTags = () => {
  showTags.value = !showTags.value;
}

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/api/tags');

    if (!response.ok) {
      throw new Error('Failed to fetch tags');
    }
    const tags: ArtWorkTag[] = await response.json();

    // Organize tags by type
    tags.forEach((tag) => {
      tag.tagType = tag.tagType.toUpperCase();
      if (!tagsByType.value[tag.tagType]) {
        tagsByType.value[tag.tagType] = [];
      }
      tagsByType.value[tag.tagType].push(tag);
    });
    console.log(tags)
  } catch (error) {
    console.error(error);
  }
});
// Handle image preview
const handleImagePreview = (event: Event) => {
  const fileInput = event.target as HTMLInputElement;
  const file = fileInput.files ? fileInput.files[0] : null;
  if (file) {
    artwork.value.mediaUrl = URL.createObjectURL(file); // Show preview
  }
};

// Handle file upload to Cloudinary
const handleFileUpload = async () => {
  const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
  const file = fileInput?.files ? fileInput.files[0] : null;

  if (file) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', 'Evaaamo'); // Cloudinary upload preset
    formData.append('cloud_name', 'diyvre9ie'); // Cloudinary cloud name

    try {
      const response = await axios.post('https://api.cloudinary.com/v1_1/diyvre9ie/image/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      // Set the mediaUrl to the URL returned by Cloudinary
      artwork.value.mediaUrl = response.data.secure_url;
    } catch (error) {
      console.error('Error uploading file:', error);
      alert('Error uploading image');
    }
  }
};

// Helper function to handle tag selection
const handleTagChange = (tag: ArtWorkTag, checked: boolean) => {
  if (checked) {
    // If the tag is checked, add it to the artwork.tags array
    artwork.value.tags.push(tag);
  } else {
    // If unchecked, remove it from the artwork.tags array
    artwork.value.tags = artwork.value.tags.filter((t) => t.tagId !== tag.tagId);
  }
};

const handleSubmit = async (event:Event) => {
  event.preventDefault()
  loading.value = true;

  try {
    // If there is an image, upload it
   // if (artwork.value.mediaUrl) {
   //   await handleFileUpload();  // This will upload the image and update mediaUrl
   // }

    // Prepare the data payload
    const payload = {
      ...artwork.value,
      tags: artwork.value.tags.map(tag => ({
        tagType: tag.tagType.toUpperCase(),
        tagValue: tag.tagValue,
      })),
    };

    // Send the artwork data to the backend
    await axios.post('http://localhost:8080/api/addArtwork', payload);

    alert('Artwork submitted successfully!');
    // Clear the form
    artwork.value = {
      title: '',
      description: '',
      price: 0,
      mediaUrl: '',
      type: true,
      tags: [],
      isSold: false,
    };
  } catch (error) {
    console.error(error);
    alert('Error submitting artwork');
  } finally {
    loading.value = false;
  }
};

</script>

<template>
  <FormComponent formWidth="w-full" title="Tilføj Kunstværk" @submit="handleSubmit"  class="w-screen" >
    <div class="input wrapper flex flex-row">
        <div class="left column flex flex-col">
          <div v-if="artwork.mediaUrl">
            <img :src="artwork.mediaUrl" alt="Selected artwork" class="w-48 md:w-64 lg:w-82" />
          </div>

          <input
              label="Billede"
              @change="handleImagePreview"
              accept="image/*"
              name="mediaUrl"
              type="file"
          >

        </div>
      <div class="right coloumn flex flex-col ">

        <!-- tags -->
        <div class="tags-section relative ">
          <button
          type="button"
          class="toggle-tags-btn flex items-center"
          @click="toggleTags"
          >
            <i :class="['fas', showTags ? 'fa-chevron-up' : ' fa-chevron-down']"></i>
          <span class="ml-2">Tags</span>
          </button>
          <div :class="['tags-container', {'open': showTags}]">
        <div v-for="(tags, type) in tagsByType" :key="type" class="tag-group flex flex-col text-[#EAEAEA]">
          <div>
            <h3>{{ type }}</h3>
          </div>
          <div class="flex flex-row">
            <div v-for="tag in tags" :key="tag.tagId">
              <label>
                <input
                    type="checkbox"
                    :value="tag.tagValue"
                    :checked="artwork.tags.some(t => t.tagId === tag.tagId)"
                    @change="(event) => handleTagChange(tag, event?.target?.checked)"

                />
                {{ tag.tagValue }}
              </label>
            </div>
        </div>
          </div>
        </div>
        </div>
        <BasicInputComponent
            label="Titel"
            name="title"
            v-model="artwork.title"
            type="text"
            placeholder="Unanvgivet"
        ></BasicInputComponent>
    <BasicInputComponent
        label="Beskrivelse"
        name="description"
        type="textArea"
        v-model="artwork.description"
        placeholder="Tilføj beskrivelse ..."
    ></BasicInputComponent>
    <BasicInputComponent
        label="Pris"
        name="price"
        type="number"
        v-model="artwork.price"
        placeholder="Indtast pris ..."
    ></BasicInputComponent>
    <BasicInputComponent
        label="Solgt?"
        name="sold"
        v-model="artwork.isSold"
        type="checkbox"
    ></BasicInputComponent>

    <BasicInputComponent
        label="Er dette et maleri?"
        name="type"
        v-model="artwork.type"
        type="checkbox"
    ></BasicInputComponent>

    </div>
    </div>

    <!-- Submit Button -->
    <FormButtonComponent :loading="loading">Gem</FormButtonComponent>
  </FormComponent>
</template>

<style scoped>
.tags-section {
  position: relative;
}
.tags-container {
  position:absolute;
  top: 100%;
  left: 0;
  width: 100%;
  max-height: 0;
  overflow: hidden;
  z-index: 10;
  transition: max-height 0.3s ease;
  opacity: 0;
  background-color: #4a4a4a;

}
.tags-container.open {
  max-height: fit-content;
  opacity: 0.8;
}
</style>
