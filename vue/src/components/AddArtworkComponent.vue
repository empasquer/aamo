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
const emit = defineEmits<{
  (event: 'close'): void;
}>();

const tagTranslations = {
  SIZE: 'Størrelse',
  COLOR: 'Farve',
  THEME: 'Tema',
}
const handleClose = () => {
  const container = document.querySelector('.tags-container');
  if (container) {
    // Add a closing class for reverse animation
    container.classList.add('closing');

    // Wait for the animation to complete before emitting the event
    setTimeout(() => {
      emit('close'); // Re-emit the event to the parent
      container.classList.remove('closing'); // Reset class after animation
    }, 500); // Match the duration of your closing animation
  }
  emit('close'); // Re-emit the event to the parent

};
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

// Trigger the hidden file input when the user clicks the image preview

const fileInput = ref<HTMLInputElement | null>(null);

const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click(); // Trigger the file input click event
  }
};
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
  <PopOpModalComponent @close="handleClose">
    <FormComponent formWidth="w-full" title="Tilføj Kunstværk" @submit="handleSubmit" class="w-full overflow-hidden">
      <div class="grid grid-cols-3 gap-4 pl-20 mt-2">

        <!-- Left Column: Image Preview & File Input -->
        <div class="left-column flex flex-col justify-center items-center mb-44 relative">
          <!-- Left Column: Image Preview & File Input -->
          <div class="left-column flex flex-col justify-center items-center mb-44 relative">

            <!-- Image preview or gray box acting as a button -->
            <div
                class=" w-36 h-44 lg:w-56 lg:h-44 flex justify-center items-center bg-transparent cursor-pointer relative"
                @click="triggerFileInput"
                :class="{'border-4 border-dashed border-gray-400': !artwork.mediaUrl}"
            >
              <!-- If an image is selected, show the preview -->
              <div v-if="artwork.mediaUrl">
                <img :src="artwork.mediaUrl" alt="Selected artwork" class="w-full h-full object-contain" />
              </div>
              <!-- If no image is selected, show 'Vælg billede' -->
              <span v-else class="text-gray-500">Vælg billede</span>
              <!-- Hidden file input -->
              <input
                  ref="fileInput"
                  @change="handleImagePreview"
                  accept="image/*"
                  name="mediaUrl"
                  type="file"
                  required
                  class="absolute inset-0 opacity-0 cursor-pointer"
              />
            </div>
          </div>
          </div>

        <!-- Middle Column: Title, Description, and other form fields -->
        <div class="middle-column flex flex-col sm:40 lg:w-72">
          <BasicInputComponent
              label="Titel"
              name="title"
              v-model="artwork.title"
              type="text"
              placeholder="Unavngivet"
              width="w-full"
          />
          <BasicInputComponent
              label="Beskrivelse"
              name="description"
              type="textArea"
              v-model="artwork.description"
              placeholder="Tilføj beskrivelse ..."
              width="w-full"
          />
          <!-- Submit Button -->
          <div class="pt-6 justify-center flex">
          <FormButtonComponent :loading="loading" type="submit" class=" border w-16  flex justify-center ">Gem</FormButtonComponent>
          </div>
        </div>

        <!-- Right Column: Price, Sold Checkbox, Tags -->
        <div class="right-column flex flex-col  text-left">
          <BasicInputComponent
              label="Pris"
              name="price"
              type="number"
              v-model="artwork.price"
              placeholder="Indtast pris ..."
              width="w-32"
          />
          <div class="flex flex-row text-left mb-4">
            <BasicInputComponent
                label="Maleri"
                name="type"
                v-model="artwork.type"
                type="checkbox"
                width="w-8"
                class="checkbox"
            />
            <BasicInputComponent
                label="Solgt"
                name="sold"
                v-model="artwork.isSold"
                type="checkbox"
                width="w-8"
            />
          </div>

          <!-- Tags Section -->
          <div class="tags-section relative text-left">
            <button
                type="button"
                class="toggle-tags-btn flex items-center p-0 bg-transparent text-white"
                @click="toggleTags"
            >

              <i :class="['fas', showTags ? 'fa-chevron-left' : 'fa-chevron-right']" style="color: white"></i>
              <span class="ml-2 text-white">Tags</span>
            </button>
            <div :class="['tags-container', { open: showTags }, 'flex', 'right-[8em]', 'sm:right-[12em]', 'md:right-[16em]', 'lg:right-[24em]']">

              <div v-for="(tags, type) in tagsByType" :key="type" class="tag-group pl-24  flex flex-col text-white">

                <div class="flex">
                  <h3 class="text-white font-inter underline pb-4">{{  tagTranslations[type] || type  }}</h3>
                </div>

                <div class="flex flex-col w-auto h-40 justify-between overflow-y-scroll scrollbar text-white">
                  <div v-for="tag in tags" :key="tag.tagValue" class=" pb-2  text-white">
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
                <div class="new-tag  flex pt-4">
                  <input
                      type="text"
                      v-model="newTags[type]"
                      placeholder="tilføj nyt tag"
                      class="w-28 p-0.5 pl-1.5"
                  />
                  <button type="button" @click="addTag(type)" class="p-0  ml-2.5 bg-transparent">
                    <i class="fa-solid fa-plus text-white"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>


    </FormComponent>
  </PopOpModalComponent>
</template>
<style scoped>
.tags-section {
  position: relative;

}
.tags-container {

  top: -12em;

  overflow: hidden;


opacity: 0;
  background-color: black;
  width: auto;
  max-height: 50em;
  padding: 1em;




}
.tags-container.open {
opacity: 1;
  z-index: 10;
  position:absolute;


}


.scrollbar {
  scrollbar-width:thin;

}
.checkbox-size {
  transform: scale(2); /* Adjust size by scaling */
  transform-origin: center;
}
.checkbox {
  padding-right: 2em;
}
</style>
