<script setup lang="ts">
import { ref, onMounted } from 'vue';
import FormComponent from "./FormComponent.vue";
import PopOpModalComponent from "./PopOpModalComponent.vue";
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
  tagId?: number;
}

const loading = ref(false);
const tagsByType = ref<Record<string, ArtWorkTag[]>>({});
const showTags = ref(false);
const newTags = ref<Record<string, string>>({});


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

//Add new tag
const addTag = (tagType: string) => {
  console.log('newTags:', newTags.value);
  const newTagValue = newTags.value[tagType]?.trim();
  console.log('newTagValue:', newTagValue);
  if(newTagValue && !tagsByType.value[tagType]?.some(tag => tag.tagValue === newTagValue)) {
    const newTag: ArtWorkTag = {
      tagType,
      tagValue: newTagValue,
    };

    tagsByType.value[tagType].unshift(newTag);  // unshift adds it at the start
    artwork.value.tags.unshift(newTag);
    newTags.value[tagType] ='';
  }
  console.log(newTagValue)
};

const sizeTagCount =  () => {
  return artwork.value.tags.filter(tag => tag.tagType === 'SIZE').length;
}

const handleSubmit = async (event:Event) => {
  event.preventDefault()
  loading.value = true;
  const count = sizeTagCount();
  if (count < 1) {
    alert("Der skal vælges 1 Størrelse")
    loading.value = false;
    return;
  }
  if (count > 1) {
    alert("Vælg kun 1 Størrelse")
    loading.value = false;
    return
  }
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
  <PopOpModalComponent>
  <FormComponent formWidth="w-full" title="Tilføj Kunstværk" @submit="handleSubmit"  class="w-screen w-72 " >
    <div class="input wrapper flex flex-row justify-between ml-28 mr-96 mt-2">
        <div class="left column flex flex-col pr-6 w-72">
          <div v-if="artwork.mediaUrl">
            <img :src="artwork.mediaUrl" alt="Selected artwork" class="w-48 md:w-64 lg:w-82" />
          </div>

          <input
              label="Billede"
              @change="handleImagePreview"
              accept="image/*"
              name="mediaUrl"
              type="file"
              required
          >

        </div>
      <div class="middle column flex flex-col pr-6 w-72">
        <BasicInputComponent
            label="Titel"
            name="title"
            v-model="artwork.title"
            type="text"
            placeholder="Unanvgivet"
            class="w-full"
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

      </div>
      <div class="right coloumn flex flex-col w-72 ">


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

        <!-- tags -->
        <div class="tags-section relative ">
          <button
              type="button"
              class="toggle-tags-btn flex items-center p-0 ml-2.5 bg-transparent text-white"
              @click="toggleTags"
          >
            <span class="ml-2 text-white">Tags</span>
            <i :class="['fas', showTags ? 'fa-chevron-left' : ' fa-chevron-right']" style="color: white"></i>

          </button>
          <div :class="['tags-container', {'open': showTags}]">
            <div v-for="(tags, type) in tagsByType" :key="type" class="tag-group flex flex-col text-white]">
              <div class="flex ">
                <h3 class="text-white">{{ type }}</h3>
              </div>
              <div class="flex flex-row w-60 overflow-x-scroll scrollbar text-white ">
                <div v-for="tag in tags" :key="tag.tagValue" class="pl-4 text-white">
                  <label class="text-white">
                    <input
                        type="checkbox"
                        :value="tag.tagValue"
                        :checked="artwork.tags.some(t => t.tagValue === tag.tagValue)"
                        @change="(event) => handleTagChange(tag, event?.target?.checked)"

                    />
                    {{ tag.tagValue }}
                  </label>
                </div>
              </div>

              <div class="new-tag p-0.5 flex ">
                <input
                    type="text"
                    v-model="newTags[type]"
                    placeholder="tilføj nyt tag"
                    class="w-28 p-0.5"
                >
                <button type="button" @click="addTag(type)" class=" p-0 ml-2.5 bg-transparent" >
                  <i class="fa-solid fa-plus text-white"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Submit Button -->
    <FormButtonComponent :loading="loading" type="submit" class="bg-amber-50 flex justify-items-end">Gem</FormButtonComponent>
  </FormComponent>
  </PopOpModalComponent>
</template>

<style scoped>
.tags-section {
  position: relative;

}
.tags-container {
  position:absolute;
  top: -12em;
  left: 7em;
  overflow: hidden;
  z-index: 10;
  transition: max-height 0.3s ease;
opacity: 0;
  background-color: black;
  width: 40em;
  max-height: 50em;


}
.tags-container.open {
opacity: 1;
}

.scrollbar {
  scrollbar-width: thin;
}
</style>
